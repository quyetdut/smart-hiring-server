package com.smartdev.iresource.authentication.service;

import com.smartdev.iresource.authentication.entity.ConfirmationToken;
import com.smartdev.iresource.authentication.entity.User;
import com.smartdev.iresource.authentication.enums.ConfirmToken;
import com.smartdev.iresource.authentication.enums.ConfirmationType;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;

public interface ConfirmationTokenService {
    void saveConfirmationToken(ConfirmationToken confirmationToken);
    ConfirmToken confirmToken(String token);
    void sendToken(User user, ConfirmationType type) throws MessagingException;
    Boolean resendToken(String email, ConfirmationType type);
}
