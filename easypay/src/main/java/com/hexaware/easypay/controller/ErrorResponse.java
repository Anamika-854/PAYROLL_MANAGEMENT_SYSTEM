package com.hexaware.easypay.controller;
public class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

	public ErrorResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ErrorResponse [message=" + message + "]";
	}

    
    // Getter
}