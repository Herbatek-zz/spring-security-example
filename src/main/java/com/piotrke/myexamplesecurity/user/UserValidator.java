package com.piotrke.myexamplesecurity.user;

import com.piotrke.myexamplesecurity.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    void validBeforeCreate(UserDto userDto) {
        if (!isConfirmPasswordIdentical(userDto.getPassword(), userDto.getMatchingPassword())) {
            log.debug("The passwords are not the same");
            throw new BadRequestException("The passwords are not the same");
        }
        if (!isEmailUnique(userDto.getEmail())) {
            log.debug("There is an existing account with email: {}", userDto.getEmail());
            throw new BadRequestException("There is an account with this email");
        }
    }

    private boolean isConfirmPasswordIdentical(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    private boolean isEmailUnique(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }
}
