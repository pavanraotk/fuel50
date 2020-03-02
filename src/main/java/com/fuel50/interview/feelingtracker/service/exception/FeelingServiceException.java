package com.fuel50.interview.feelingtracker.service.exception;

public class FeelingServiceException extends Exception {
    private String code;
    private String message;

    public FeelingServiceException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public FeelingServiceException(String code, String message, Throwable cause) {
        super(cause);
        this.code = code;
        this.message = message;
    }

    public FeelingServiceException(Throwable cause, boolean enableSuppression, boolean writableStackTrace, String code, String message) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
