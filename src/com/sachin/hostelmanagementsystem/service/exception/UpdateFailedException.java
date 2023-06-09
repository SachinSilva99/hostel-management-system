package com.sachin.hostelmanagementsystem.service.exception;

public class UpdateFailedException extends Exception{
    public UpdateFailedException() {
    }

    public UpdateFailedException(String message) {
        super(message);
    }

    public UpdateFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateFailedException(Throwable cause) {
        super(cause);
    }

    public UpdateFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
