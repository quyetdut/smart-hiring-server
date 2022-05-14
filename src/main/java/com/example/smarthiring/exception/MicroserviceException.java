package com.example.smarthiring.exception;

public class MicroserviceException extends RuntimeException{
    private Integer DevCode;
    public MicroserviceException(String message, Integer devCode) {
        super(message);
        this.DevCode = devCode;
    }
}
