package com.example.smarthiring.exception;

import lombok.Getter;

@Getter
public class NotFoundExceptionDev extends RuntimeException {
    private Integer DevCode;

    public NotFoundExceptionDev(String message, Integer devCode) {
        super(message);
        this.DevCode = devCode;
    }
}
