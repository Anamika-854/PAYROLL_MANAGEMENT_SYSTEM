package com.hexaware.easypay.controller;
// Create RegisterRequest.java class
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String employeeCode;
    private String role;

    // Constructor
    public RegisterRequest() {}

    

    public RegisterRequest(String username, String password, String email, String employeeCode, String role) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.employeeCode = employeeCode;
		this.role = role;
	}



	// Getters and setters
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }
    
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}