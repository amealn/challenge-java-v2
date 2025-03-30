package com.challenge.v2.controllers.model;

public class Response<T> {
	
	private T data;
    private boolean success;
    private String errorMessage;
    private String errorCode;

    public Response(T data) {
        this.data = data;
        this.success = true;
    }

    public Response(String errorMessage, String errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.success = false;
    }

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}    

}
