package com.piotrke.myexamplesecurity.security.verificationToken;

import com.piotrke.myexamplesecurity.abstracts.BaseToken;
import com.piotrke.myexamplesecurity.user.User;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;


@Entity
@NoArgsConstructor
class VerificationToken extends BaseToken {

    VerificationToken(User user, String token) {
        super(user, token);
    }

}
