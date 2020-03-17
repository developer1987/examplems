package ru.diasoft.micro.service;

import ru.diasoft.micro.dto.UserDto;
import ru.diasoft.micro.persist.UserEntity;

/**
 * @author Zverev Artem
 */
public interface UserService {

    UserDto create(UserDto user);

    Iterable<UserDto> findAll();
}
