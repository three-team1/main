package com.main.miniproject.user.entity;

public enum Role {
	USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

	private String key;

	Role(String key) {
	    this.key = key;
	}

	public String getKey() {
	    return key;
	}
	    
}
