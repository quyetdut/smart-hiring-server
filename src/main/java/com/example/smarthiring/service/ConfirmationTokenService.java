package com.example.smarthiring.service;

import com.example.smarthiring.entity.ConfirmationToken;
import com.example.smarthiring.entity.User;
import com.example.smarthiring.enums.ConfirmToken;
import com.example.smarthiring.enums.ConfirmationType;

import javax.mail.MessagingException;

public interface ConfirmationTokenService {
    void saveConfirmationToken(ConfirmationToken confirmationToken);
    ConfirmToken confirmToken(String token);
    void sendToken(User user, ConfirmationType type) throws MessagingException;
    Boolean resendToken(String email, ConfirmationType type);
}
