package ru.diasoft.micro.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.diasoft.micro.dto.UserDto;
import ru.diasoft.micro.persist.UserEntity;
import ru.diasoft.micro.service.UserService;

import javax.validation.Valid;

/**
 * @author Zverev Artem
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/v1/user")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDto user) {
        return ResponseEntity.ok(userService.create(user));
    }

    @GetMapping(value = "/v1/users")
    public ResponseEntity<Iterable<UserDto>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }

}
