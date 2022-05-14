package com.smartdev.iresource.authentication.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.text.*;


@Getter
@NoArgsConstructor
public class ResponseBodyException {
    private boolean Result;
    private HttpStatus StatusCode;
    private Integer DevCode;
    private String message;

    public ResponseBodyException(boolean result, HttpStatus statusCode, Integer devCode, String message) {
        Result = result;
        StatusCode = statusCode;
        DevCode = devCode;
        this.message = message;
    }
}
