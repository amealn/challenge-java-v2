package com.challenge.v2.security.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.v2.security.jwt.JwtService;
import com.challenge.v2.security.model.LoginRequest;
import com.challenge.v2.security.model.PasswordChangeRequest;
import com.challenge.v2.security.model.User;
import com.challenge.v2.security.repository.LoginDao;
import com.challenge.v2.security.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {
	
	private final String LOGIN_ATTEMPTS_PREFIX = "login_attempts:";
    private final int MAX_ATTEMPTS = 3;
    private final long LOCKOUT_DURATION_MINUTES = 15;
    private final long PASSWORD_EXPIRATION_MONTHS = 6;
	
	@Autowired
	private LoginDao loginDao;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private RedisTemplate<String, Long> redisTemplateLong;

    /**
     * During registration, ensures username and email do not exist, then insert a new user to the DDBB
     *
     * @param User         A user.
     */
	@Override
	@Transactional
	public String register(User user) {
        if (loginDao.findByUsername(user.getUsername()).isPresent() || loginDao.findByEmail(user.getEmail()).isPresent()) {
            return "Username or email already exists.";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegistrationDate(LocalDateTime.now());
        user.setEnabled(true);
        user.setPasswordExpired(false);
        user.setAccountLocked(false);

        loginDao.insert(user);
        return "User registered successfully.";
	}

	/**
     * Checks if the username exists, then if the password is expired, then checks the number of login attempts, then if the user is enabled and unlocked, then createds a jwt token and saves last login date. Otherwise, increments the failed attempts
     *
     * @param LoginRequest         A login request.
     */
	@Override
	@Transactional
	public String login(LoginRequest loginRequest) {		
		Optional<User> userOptional = loginDao.findByUsername(loginRequest.getUsername());
        if (userOptional.isEmpty()) {
            return "User not found.";
        }

        User user = userOptional.get();
        
        if (user.getLastPasswordChange() != null && ChronoUnit.MONTHS.between(user.getLastPasswordChange(), LocalDateTime.now()) >= PASSWORD_EXPIRATION_MONTHS) {
                user.setEnabled(false);
                user.setPasswordExpired(true);
                loginDao.save(user);
                return "Password expired. You will need to change it.";
        }
        
        if (!user.getEnabled()) {
        	throw new DisabledException("Account is disabled.");
        }
        
        if (user.getAccountLocked()) {
        	throw new LockedException("Account is locked.");
        }

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            user.setLastLoginDate(LocalDateTime.now());
            redisTemplateLong.delete(LOGIN_ATTEMPTS_PREFIX + user.getUsername());
            loginDao.save(user);

            return jwtService.generateToken(authentication);
            
        } catch (BadCredentialsException e) {
        	Long attempts = redisTemplateLong.opsForValue().increment(LOGIN_ATTEMPTS_PREFIX + user.getUsername());
            
            if (attempts >= MAX_ATTEMPTS) {
                user.setAccountLocked(true);
                user.setAccountLockedUntil(LocalDateTime.now().plusMinutes(LOCKOUT_DURATION_MINUTES));
                loginDao.save(user);
            } else {
                redisTemplateLong.expire(LOGIN_ATTEMPTS_PREFIX + user.getUsername(), LOCKOUT_DURATION_MINUTES, TimeUnit.MINUTES);
            }
            return "Invalid credentials.";
        }
	}
	
	@Override
	public String logout() {
		SecurityContextHolder.clearContext();
		return "Logged out successfully.";
	}

	@Override
	public User getUserInfo(String username) {
		Optional<User> userOptional = loginDao.findByUsername(username);
        return userOptional.orElse(null);
	}
	
	@Override
	public String changePassword(PasswordChangeRequest passwordChangeRequest) {
        Optional<User> userOptional = loginDao.findByUsername(passwordChangeRequest.getUsername());
        if (userOptional.isEmpty()) {
            return "User not found.";
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(passwordChangeRequest.getOldPassword(), user.getPassword())) {
            return "Incorrect old password.";
        }

        user.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
        user.setLastPasswordChange(LocalDateTime.now());
        user.setPasswordExpired(false);
        loginDao.save(user);

        return "Password changed successfully.";
    }
}
