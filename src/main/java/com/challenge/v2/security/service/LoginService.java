package com.challenge.v2.security.service;

import com.challenge.v2.security.model.LoginRequest;
import com.challenge.v2.security.model.PasswordChangeRequest;
import com.challenge.v2.security.model.User;

public interface LoginService {
	
	public String register(User user);
	
	public String login(LoginRequest loginRequest);
	
	public String logout();
	
	public User getUserInfo(String username);
	
	public String changePassword(PasswordChangeRequest passwordChangeRequest);
	
}
