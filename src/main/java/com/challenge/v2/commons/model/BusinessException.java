package com.challenge.v2.commons.model;

import com.challenge.v2.commons.model.enums.ErrorCode;

public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = -1863469877868498994L;
	
	private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}