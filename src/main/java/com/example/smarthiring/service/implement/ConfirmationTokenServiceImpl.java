package com.example.smarthiring.service.implement;

//import com.example.smarthiring.email.EmailService;
import com.example.smarthiring.entity.ConfirmationToken;
import com.example.smarthiring.entity.User;
import com.example.smarthiring.enums.ConfirmToken;
import com.example.smarthiring.enums.ConfirmationType;
import com.example.smarthiring.exception.RegistrationFailedException;
import com.example.smarthiring.repository.ConfirmationTokenRepository;
import com.example.smarthiring.repository.UserRepository;
import com.example.smarthiring.service.ConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

/*68-116*/

@Service
@RequiredArgsConstructor
@Transactional
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final UserRepository userRepository;
//    private final EmailService emailService;

    @Value("${application.mail.timeExpired}")
    private long timeExpired;

    @Override
    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    @Override
    public ConfirmToken confirmToken(String token) {
        Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.findByToken(token);

        if (confirmationToken.isEmpty()) {
            return ConfirmToken.INVALID_TOKEN;
        }

        if (confirmationToken.get().getConfirmedAt() != null) {
            return ConfirmToken.EMAIL_ALREADY_EXISTS;
        }

        LocalDateTime expiredAt = confirmationToken.get().getExpireAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            return ConfirmToken.TOKEN_EXPIRED;
        }

        confirmationToken.get().setConfirmedAt(LocalDateTime.now());
        confirmationTokenRepository.save(confirmationToken.get());

        userRepository.enabledUser(confirmationToken.get().getUser().getEmail());

        return ConfirmToken.CONFIRMED;
    }

    @Override
    public void sendToken(User user, ConfirmationType type) throws MessagingException {
//        try {
//            String randomDigit = String.format("%06d", new Random().nextInt(999999));
//            ConfirmationToken confirmationToken = new ConfirmationToken(
//                    randomDigit,
//                    LocalDateTime.now(),
//                    LocalDateTime.now().plusMinutes(timeExpired),
//                    user,
//                    type
//            );
//
//            String emailContent = String.format(type.getMessage(), user.getEmail().substring(0, user.getEmail().lastIndexOf("@")), randomDigit, timeExpired);
//            emailService.send(
//                    user.getEmail(),
//                    "Confirm your email!",
//                    emailContent
//            );
//            confirmationTokenRepository.save(confirmationToken);
//        } catch (MessagingException | MailAuthenticationException e) {
//            throw new MessagingException("Failed to send email");
//        }
    }

    @Override
    public Boolean resendToken(String email, ConfirmationType type) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RegistrationFailedException(
                        "Email " + email + " doesn't exists.", 1012
                ));

        if (!user.isEnabled()) {
            confirmationTokenRepository.removeConfirmationTokenByUser(user);
            try {
                this.sendToken(user, type);
            } catch (MessagingException e) {
                return false;
            }
        }

        return true;
    }
}
