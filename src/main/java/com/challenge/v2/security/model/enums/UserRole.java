package com.challenge.v2.security.model.enums;

public enum UserRole {
	
	CONSULT("CONSULT"),
	UPDATE("UPDATE"),
	DELETE("DELETE"),
	ADMIN("ADMIN");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

}
