package com.lidapinchuk.exception;

public class DatabaseException extends RuntimeException {

    public DatabaseException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
