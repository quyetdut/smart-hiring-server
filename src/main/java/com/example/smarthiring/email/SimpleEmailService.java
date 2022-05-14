package com.smartdev.iresource.authentication.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
public class SimpleEmailService implements EmailService {
    private final JavaMailSender mailSender;

    public SimpleEmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(String recipient, String subject, String message) throws MessagingException, MailAuthenticationException {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(new InternetAddress(recipient));
            helper.setSubject(subject);
            helper.setText(message);
            helper.setFrom(new InternetAddress("noreply.support@smartdev.com"));
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.info("error mail: {}", e.getCause());
            log.info("error mail: {}", e.getLocalizedMessage());
            log.info("error mail: {}", e.getStackTrace());
            e.printStackTrace();
            throw new MessagingException("Failed to send email!");
        } catch (MailAuthenticationException e) {
            log.info("error mail: {}", e.getCause());
            log.info("error mail: {}", e.getLocalizedMessage());
            log.info("error mail: {}", e.getStackTrace());
            e.printStackTrace();
            throw new MailAuthenticationException("Failed to send email!");
        }
    }
}
