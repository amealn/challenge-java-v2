package com.challenge.v2.security.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.v2.controllers.model.Response;
import com.challenge.v2.security.model.LoginRequest;
import com.challenge.v2.security.model.PasswordChangeRequest;
import com.challenge.v2.security.model.User;
import com.challenge.v2.security.model.enums.UserRole;
import com.challenge.v2.security.service.LoginService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {    
    
    @Autowired
    private LoginService loginService;
    
    @PostMapping("/register")
    @Operation(summary = "Registers a user")
    public Response<String> register(@Valid @RequestBody User user) {
    	return new Response<>(loginService.register(user));
    }

    @PostMapping(path = "/login")
	@Operation(summary = "Logs in to api")
    public Response<String> login(@Valid @RequestBody LoginRequest loginRequest) {
        return new Response<>(loginService.login(loginRequest));
    }
    
    @PostMapping(path = "/logout")
	@Operation(summary = "Logs out from api")
    public Response<String> logout() {        
        return new Response<>(loginService.logout());
    }
    
    @PostMapping(path = "/getUserInfo")
	@Operation(summary = "Gets user info")
    public Response<User> getUserInfo(@NotNull @RequestBody String username) {
        return new Response<>(loginService.getUserInfo(username));
    }
    
    @GetMapping(path = "/getRoles")
	@Operation(summary = "Gets list of roles")
    public Response<List<UserRole>> getRoles() {
        return new Response<>(Arrays.asList(UserRole.values()));
    }
    
    @PostMapping(path = "/changePassword")
    @Operation(summary = "Change old password")
    public Response<String> changePassword(@Valid @RequestBody PasswordChangeRequest passwordChangeRequest) {
        return new Response<>(loginService.changePassword(passwordChangeRequest));
    }
    
}
