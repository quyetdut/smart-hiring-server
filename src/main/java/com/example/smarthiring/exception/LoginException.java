package com.example.smarthiring.exception;

import lombok.Getter;

@Getter
public class LoginException extends RuntimeException{
    private Integer DevCode;

    public LoginException(String message, Integer devCode) {
        super(message);
        this.DevCode = devCode;
    }
}
