package com.smartdev.iresource.authentication.enums;

import lombok.Getter;
import lombok.Setter;

public enum ConfirmationType {
    SIGN_UP("Hi %s," +
            "\n\nThis is your confirmation code for register new account: %s." +
            "\nThe code will be expired in %d minutes."),
    FORGOT_PASSWORD("Hi %s," +
            "\n\nThis is your confirmation code to reset your password: %s." +
            "\nThe code will be expired in %d minutes.");

    @Getter
    @Setter
    private String message;

    ConfirmationType(String message) {
        this.message = message;
    }
}
