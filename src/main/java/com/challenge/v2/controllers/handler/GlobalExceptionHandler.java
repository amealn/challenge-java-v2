package com.challenge.v2.controllers.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.challenge.v2.commons.model.BusinessException;
import com.challenge.v2.commons.model.enums.ErrorCode;
import com.challenge.v2.controllers.model.Response;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Response<?> handleBusinessException(BusinessException ex) {
        return new Response<>(ex.getErrorCode().getErrorMessage(), ex.getErrorCode().getErrorCode());
    }

    @ExceptionHandler(RuntimeException.class)
    public Response<?> handleRuntimeException(RuntimeException ex) {
        return new Response<>(ex.getMessage(), ErrorCode.ERROR_RUNTIME_EXCEPTION.getErrorCode());
    }
}