package com.pluralsight.ridetracker.util;

public class ServiceError {
    private int code;
    private String message;

    public ServiceError(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
