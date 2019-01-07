package com.piotrke.myexamplesecurity.abstracts;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public abstract class BaseDto implements Serializable {

    private long id;
}
