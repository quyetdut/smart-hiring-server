package com.example.smarthiring.exceptions;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private Integer DevCode;

    public NotFoundException(String message, Integer devCode) {
        super(message);
        this.DevCode = devCode;
    }
}
