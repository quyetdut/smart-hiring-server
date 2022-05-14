package com.smartdev.common.enums;

import lombok.Getter;

@Getter
public enum ResponseResult implements IResponseResult {
    SUCCESS(200, "Sucessfully!", true);

    ResponseResult(int code, String message, boolean result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public void customMessage(String message) {
        this.message = message;
    }

    private int code;
    private String message;
    private Boolean result;
}
