package com.challenge.v2.security.model;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.challenge.v2.security.model.enums.UserRole;
import com.challenge.v2.security.model.validator.ValidBirthdate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Document(collection = "users")
public class User {
	
	@MongoId
    private String id;
	
	@NotNull
    @Size(min = 3, max = 30)
    private String username;
	
	@NotNull
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
    message = "Password must have at least 8 characters, one number, one uppercase letter, one lowercase letter and one special character.")
    private String password;
	
	@NotNull
    @Size(min = 1, max = 30)
    private String firstName;
    
	@NotNull
    @Size(min = 1, max = 30)
    private String lastName;
    
    @NotNull
    @Email
    private String email;
    
    private String phoneNumber;    
    
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/\\d{4}$", message = "Birthdate must be in dd/MM/yyyy format")
    @ValidBirthdate
    private String birthdate;
    
    @NotNull
    private UserRole role;
    
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime registrationDate;
    
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime lastLoginDate;  
    
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime lastPasswordChange; 
    
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Boolean enabled;
    
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Boolean passwordExpired;
    
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Boolean accountLocked;
    
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime accountLockedUntil;
    
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}	
	
	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}
	
	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	public LocalDateTime getLastLoginDate() {
		return lastLoginDate;
	}
	
	public void setLastLoginDate(LocalDateTime lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}	
	
	public LocalDateTime getLastPasswordChange() {
		return lastPasswordChange;
	}

	public void setLastPasswordChange(LocalDateTime lastPasswordChange) {
		this.lastPasswordChange = lastPasswordChange;
	}

	public UserRole getRole() {
		return role;
	}
	
	public void setRole(UserRole role) {
		this.role = role;
	}
	
	public Boolean getEnabled() {
		return enabled;
	}
	
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public Boolean getPasswordExpired() {
		return passwordExpired;
	}
	
	public void setPasswordExpired(Boolean passwordExpired) {
		this.passwordExpired = passwordExpired;
	}
	
	public Boolean getAccountLocked() {
		return accountLocked;
	}
	
	public void setAccountLocked(Boolean accountLocked) {
		this.accountLocked = accountLocked;
	}	
	
	public LocalDateTime getAccountLockedUntil() {
		return accountLockedUntil;
	}

	public void setAccountLockedUntil(LocalDateTime accountLockedUntil) {
		this.accountLockedUntil = accountLockedUntil;
	}    

}
