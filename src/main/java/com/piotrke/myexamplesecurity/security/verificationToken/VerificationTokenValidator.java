package com.piotrke.myexamplesecurity.security.verificationToken;

import com.piotrke.myexamplesecurity.exceptions.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
public class VerificationTokenValidator {

    void validBeforeActivateUser(long userId, VerificationToken verificationToken) {
        if(isEnabled(verificationToken)) {
            log.debug("User has been already confirmed");
            throw new BadRequestException("User has been already confirmed");
        }
        if(isTokenExpired(verificationToken)) {
            log.debug("Token is expired");
            throw new BadRequestException("Token is expired");
        }
        if(!isTokenMatchForUser(userId, verificationToken)) {
            log.debug("User from token doesn't match userId");
            throw new BadRequestException("It is not your token");
        }
    }

    void validBeforeResendToken(long userId, VerificationToken verificationToken) {
        if(isEnabled(verificationToken)) {
            log.debug("User has been already confirmed");
            throw new BadRequestException("User has been already confirmed");
        }
        if(!isTokenExpired(verificationToken)) {
            log.debug("Token is not expired");
            throw new BadRequestException("Token is not expired");
        }
        if(!isTokenMatchForUser(userId, verificationToken)) {
            log.debug("User from token doesn't match userId");
            throw new BadRequestException("It's not your token");
        }
    }

    private boolean isEnabled(VerificationToken verificationToken) {
        return verificationToken.getUser().isEnabled();
    }

    private boolean isTokenExpired(VerificationToken verificationToken) {
        return LocalDate.now().isAfter(verificationToken.getExpiryDate());
    }

    private boolean isTokenMatchForUser(long userId, VerificationToken verificationToken) {
        return userId == verificationToken.getUser().getId();
    }
}
