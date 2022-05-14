package com.smartdev.iresource.authentication.enums;

import com.smartdev.iresource.authentication.common.enums.IResponseResult;
import lombok.Getter;

@Getter
public enum ConfirmToken implements IResponseResult {
    INVALID_TOKEN(1004, "Invalid token", false),
    EMAIL_ALREADY_EXISTS(1005, "Email already confirm.", false),
    TOKEN_EXPIRED(1006, "Token expired!", false),
    CONFIRMED(200, "Confirmed", false);

    ConfirmToken(int code, String message, Boolean result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    private int code;
    private String message;
    private Boolean result;
}
