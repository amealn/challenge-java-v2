package com.challenge.v2.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import com.challenge.v2.security.model.enums.UserRole;
import com.challenge.v2.security.service.impl.MongoUserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER -10)
public class SecurityConfig {
	
	@Autowired
    private MongoUserDetailsServiceImpl mongoUserDetailsService;

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        	.requiresChannel(channel -> channel.anyRequest().requiresSecure())
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(request -> {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.setAllowedOrigins(List.of("*"));
                corsConfiguration.setAllowedMethods(List.of("GET", "POST"));
                corsConfiguration.setAllowedHeaders(List.of("Content-Type", "Authorization"));
                return corsConfiguration;
            }))
            .authorizeHttpRequests(auth -> {
            	
            	auth.requestMatchers(HttpMethod.POST, "/salePoint/**").authenticated();
            	auth.requestMatchers(HttpMethod.GET, "/salePoint/getAllSalePoints").authenticated();
            	auth.requestMatchers(HttpMethod.POST, "/salePointsCosts/**").authenticated();
            	auth.requestMatchers(HttpMethod.GET, "/salePointCredential/getAllSalePointsCredentials").hasAnyRole(UserRole.CONSULT.name(), UserRole.UPDATE.name(), UserRole.DELETE.name(), UserRole.ADMIN.name());
            	auth.requestMatchers(HttpMethod.POST, "/salePointCredential/deleteSalePointCredential").hasRole(UserRole.DELETE.name());
            	auth.requestMatchers(HttpMethod.POST, "/salePointCredential/saveSalePointCredential").hasAnyRole(UserRole.UPDATE.name(), UserRole.DELETE.name());
            	auth.requestMatchers(HttpMethod.POST, "/salePointCredential/updateSalePointCredential").hasAnyRole(UserRole.UPDATE.name(), UserRole.DELETE.name());
                
                //NO AUTHENTICATION
            	auth.requestMatchers(HttpMethod.POST, "/auth/login", "/auth/register", "/auth/logout", "/auth/changePassword").permitAll();
            	auth.requestMatchers(HttpMethod.GET, "/auth/getRoles").permitAll();
            	auth.requestMatchers(HttpMethod.POST, "/auth/getUserInfo").hasRole(UserRole.ADMIN.name());
            	auth.requestMatchers("/swagger-ui.html",
                        		     "/v3/api-docs/**",
                        		     "/swagger-ui/**",
                        		     "/webjars/**").permitAll();
            	auth.requestMatchers("/actuator/health").permitAll();
            	auth.requestMatchers(HttpMethod.POST, "/salePointsCosts/**").authenticated();
            	//auth.requestMatchers("/oauth2/**").permitAll() //for OAuth2
            })
            //.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults())) //for OAuth2
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
	
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(mongoUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
}