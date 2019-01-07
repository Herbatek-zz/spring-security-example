package com.piotrke.myexamplesecurity.security;


import com.piotrke.myexamplesecurity.security.verificationToken.VerificationTokenService;
import com.piotrke.myexamplesecurity.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private final VerificationTokenService verificationTokenService;
    private final JavaMailSender javaMailSender;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        log.debug("Start: RegistrationListener");

        User user = event.getUser();
        String token = UUID.randomUUID().toString();

        verificationTokenService.createVerificationToken(user, token);
        log.debug("Created token {} for user {}", token, user.getEmail());

        new Thread(() -> {
            log.debug("Sending email...");
            String confirmationUrl = event.getAppUrl() + "/users/" + user.getId() + "/confirmation?token=" + token;
            String message = "Hi, here is your token: " + confirmationUrl;

            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(user.getEmail());
            email.setSubject("Registration Confirmation");
            email.setText(message);
            javaMailSender.send(email);
            log.debug("Mail has been sent to: {}", user.getEmail());
        }).start();
    }
}
