package com.sachin.hostelmanagementsystem.service.exception;

public class ReservationFailed extends RuntimeException{
    public ReservationFailed() {
    }

    public ReservationFailed(String message) {
        super(message);
    }

    public ReservationFailed(String message, Throwable cause) {
        super(message, cause);
    }

    public ReservationFailed(Throwable cause) {
        super(cause);
    }

    public ReservationFailed(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
