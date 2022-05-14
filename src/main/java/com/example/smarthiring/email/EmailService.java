package com.smartdev.iresource.authentication.email;

import javax.mail.MessagingException;

public interface EmailService {
    void send(String recipient, String subject, String message) throws MessagingException;
}
