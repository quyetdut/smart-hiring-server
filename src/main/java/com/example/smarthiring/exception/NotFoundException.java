package com.example.smarthiring.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private Integer DevCode;
    public NotFoundException(String message) {
        super(message);
        this.DevCode = 1000;
    }

    public NotFoundException(String message, Integer devCode) {
        super(message);
        this.DevCode = devCode;
    }
}
