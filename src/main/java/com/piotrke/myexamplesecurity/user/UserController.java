package com.piotrke.myexamplesecurity.user;

import com.piotrke.myexamplesecurity.security.verificationToken.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final VerificationTokenService verificationTokenService;

    @PostMapping
    @ResponseStatus(CREATED)
    public UserDto createUser(@RequestBody @Valid UserDto userDto, HttpServletRequest request) {
        String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        return userService.createUser(userDto, appUrl);
    }

    @GetMapping("/{userId}/confirmation")
    public void confirmAccount(@PathVariable long userId, @RequestParam String token) {
        verificationTokenService.confirmUserAccount(userId, token);
    }

    @GetMapping("/{userId}/confirmation/resend")
    public void resendConfirmation(@PathVariable long userId, @RequestParam String token, HttpServletRequest request) {
        String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        verificationTokenService.resendVerificationToken(appUrl, token, userId);
    }

    @GetMapping("/{userId}/password")
    public void resetPassword(@PathVariable long userId) {

    }

    @PatchMapping("/{userId}/password")
    public void changePassword(@PathVariable long userId) {

    }


}
