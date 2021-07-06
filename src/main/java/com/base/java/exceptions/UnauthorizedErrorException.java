package com.base.java.exceptions;

public class UnauthorizedErrorException extends RuntimeException {

    public UnauthorizedErrorException(String message) {
        super(message);
    }
}
