package com.example.smarthiring.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class ExceptionResponse {

    private boolean result;
    private String message;

    public ExceptionResponse(String message) {

        this.result = false;
        this.message = message;
    }
}
