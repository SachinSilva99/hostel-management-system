package com.sachin.hostelmanagementsystem.service.exception;

public class SavingFailedException extends RuntimeException{
    public SavingFailedException() {
    }

    public SavingFailedException(String message) {
        super(message);
    }

    public SavingFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public SavingFailedException(Throwable cause) {
        super(cause);
    }

    public SavingFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
