package com.piotrke.myexamplesecurity.user;

import com.piotrke.myexamplesecurity.exceptions.NotFoundException;
import com.piotrke.myexamplesecurity.security.OnRegistrationCompleteEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserDtoConverter userDtoConverter;
    private final UserValidator userValidator;
    private final ApplicationEventPublisher eventPublisher;

    UserDto createUser(UserDto userDto, String appUrl) {
        log.debug("Start: create a user");

        userValidator.validBeforeCreate(userDto);

        User user = userDtoConverter.fromDto(userDto);
        user = userRepository.save(user);

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, appUrl));

        log.debug("Successfully created user: {}", user.getEmail());
        return userDtoConverter.toDto(user);
    }

    public UserDto save(UserDto userDto) {
        log.debug("Start: save a user dto");
        User user = userDtoConverter.fromDto(userDto);
        return save(user);
    }

    public UserDto save(User user) {
        log.debug("Start: save a user");
        User savedUser = userRepository.save(user);
        log.debug("User has been saved");
        return userDtoConverter.toDto(savedUser);
    }

    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found user with id: " + id));
    }
}
