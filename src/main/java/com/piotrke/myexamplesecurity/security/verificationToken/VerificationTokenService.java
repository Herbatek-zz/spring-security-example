package com.piotrke.myexamplesecurity.security.verificationToken;

import com.piotrke.myexamplesecurity.exceptions.NotFoundException;
import com.piotrke.myexamplesecurity.user.User;
import com.piotrke.myexamplesecurity.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final VerificationTokenValidator verificationTokenValidator;
    private final MailSender mailSender;
    private final UserService userService;

    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(user, token);
        verificationTokenRepository.save(myToken);
    }

    private VerificationToken findVerificationToken(String token) {
        return verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new NotFoundException("Not found token"));
    }

    public void resendVerificationToken(String appUrl, String token, long userId) {
        log.debug("Start: resend verification token");
        User user = userService.findById(userId);
        var newToken = findVerificationToken(token);

        verificationTokenValidator.validBeforeResendToken(userId, newToken);
        log.debug("Found token for user: {}", user.getEmail());

        newToken.setToken(UUID.randomUUID().toString());
        newToken.setExpiryDate(LocalDate.now().plusDays(1));
        verificationTokenRepository.save(newToken);
        log.debug("Generated new token");

        new Thread(() -> {
            log.debug("Sending email...");
            String confirmationUrl = appUrl + "/users/" + user.getId() + "/confirmation?token=" + newToken.getToken();
            String message = "Hi, here is your token: ";
            SimpleMailMessage emailMessage = new SimpleMailMessage();
            emailMessage.setSubject("Resend Registration Token");
            emailMessage.setText(message + confirmationUrl);
            emailMessage.setTo(user.getEmail());
            mailSender.send(emailMessage);
            log.debug("Mail has been saned to: {}", user.getEmail());
        }).start();
    }

    public void confirmUserAccount(long userId, String token) {
        log.debug("Start: confirm user account");

        var tokenFromDb = findVerificationToken(token);
        log.debug("Found token for user: {}", tokenFromDb.getUser().getEmail());

        verificationTokenValidator.validBeforeActivateUser(userId, tokenFromDb);
        User user = tokenFromDb.getUser();
        user.setEnabled(true);
        userService.save(user);
        log.debug("User {} has been enabled", user.getEmail());
    }
}
