package com.hexaware.easypay.customexceptions;

import java.time.LocalDateTime;

public class ErrorDetails {
    private LocalDateTime timestamp;
    private String errorMessage;
    private String apiPath;
    private String errorCode;

    public ErrorDetails() {
        super();
    }

    public ErrorDetails(LocalDateTime timestamp, String errorMessage, String apiPath, String errorCode) {
        super();
        this.timestamp = timestamp;
        this.errorMessage = errorMessage;
        this.apiPath = apiPath;
        this.errorCode = errorCode;
    }

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getApiPath() {
		return apiPath;
	}

	public void setApiPath(String apiPath) {
		this.apiPath = apiPath;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String toString() {
		return "ErrorDetails [timestamp=" + timestamp + ", errorMessage=" + errorMessage + ", apiPath=" + apiPath
				+ ", errorCode=" + errorCode + "]";
	}

    // Getters and Setters
    
}