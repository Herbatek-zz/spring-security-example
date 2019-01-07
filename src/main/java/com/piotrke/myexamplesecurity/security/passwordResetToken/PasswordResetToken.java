package com.piotrke.myexamplesecurity.security.passwordResetToken;

import com.piotrke.myexamplesecurity.abstracts.BaseToken;
import com.piotrke.myexamplesecurity.user.User;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
public class PasswordResetToken extends BaseToken {

    PasswordResetToken(User user, String token) {
        super(user, token);
    }
}
