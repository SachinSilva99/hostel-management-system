package com.sachin.hostelmanagementsystem.service.exception;

public class ReservationFailedException extends Exception{
    public ReservationFailedException() {
    }

    public ReservationFailedException(String message) {
        super(message);
    }

    public ReservationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReservationFailedException(Throwable cause) {
        super(cause);
    }

    public ReservationFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
