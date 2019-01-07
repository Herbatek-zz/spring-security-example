package com.piotrke.myexamplesecurity.user;

import com.piotrke.myexamplesecurity.abstracts.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class User extends BaseEntity {

    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;
    private boolean enabled;

    @ElementCollection
    private List<String> roles;

    public User() {
        super();
        enabled = false;
    }
}
