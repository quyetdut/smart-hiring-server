package com.example.smarthiring.exception;

public class SomethingWrongException extends RuntimeException {

    public SomethingWrongException(String message) {
        super(message);
    }

    public SomethingWrongException(String message, Integer Devcode) {
        super(message);
    }
}
