package com.piotrke.myexamplesecurity.user;

import com.piotrke.myexamplesecurity.abstracts.BaseDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
class UserDto extends BaseDto {

    @NotNull
    @Size(min = 1, max = 30)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 40)
    private String lastName;

    @NotNull
    @Size(min = 7, max = 25)
    private String password;

    @NotNull
    @Size(min = 7)
    private String matchingPassword;

    @Email
    @NotNull
    private String email;
}
