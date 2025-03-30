package com.challenge.v2.commons.model.enums;

public enum ErrorCode {
	
	DEFAULT_DAO_ERROR("error.code.default.dao", "There was an error in the DAO."),
    INVALID_INPUT("error.code.invalid.input", "Invalid input provided."),
    RESOURCE_NOT_FOUND("error.code.resource.not.found", "Resource not found."),
	ERROR_RUNTIME_EXCEPTION("error.code.runtime.exception", "Error during runtime");

    private final String errorCode;
    private final String errorMessage;

    ErrorCode(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
