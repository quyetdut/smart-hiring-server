package com.smartdev.iresource.authentication.exception;

import lombok.Getter;

@Getter
public class RegistrationFailedException extends RuntimeException {

    private Integer DevCode;

    public RegistrationFailedException(String message, Integer devCode) {
        super(message);
        this.DevCode = devCode;
    }
}
