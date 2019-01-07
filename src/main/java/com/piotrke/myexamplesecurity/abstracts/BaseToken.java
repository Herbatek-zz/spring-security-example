package com.piotrke.myexamplesecurity.abstracts;

import com.piotrke.myexamplesecurity.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
public abstract class BaseToken implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne
    @JoinColumn(nullable = false, unique = true, name = "user_id")
    private User user;

    private LocalDate expiryDate;

    public BaseToken(User user, String token) {
        this.user = user;
        this.token = token;
        this.expiryDate = LocalDate.now().plusDays(1);
    }
}
