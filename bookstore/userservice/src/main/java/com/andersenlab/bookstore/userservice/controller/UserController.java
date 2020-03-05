package com.andersenlab.bookstore.userservice.controller;

import com.andersenlab.bookstore.userservice.model.User;
import com.andersenlab.bookstore.userservice.model.dto.UserDTO;
import com.andersenlab.bookstore.userservice.model.dto.UserMapper;
import com.andersenlab.bookstore.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers(@RequestParam(value = "userId", required = false) Integer[] ids) {
        List<User> users = ids == null
                ? userService.getUsers()
                : userService.getUsersById(Arrays.asList(ids));
        return ResponseEntity.ok(
                users.stream().map(userMapper::toDTO).collect(Collectors.toList())
        );
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        if (userDTO.getId() != 0) {
            return ResponseEntity.badRequest().build();
        }

        User user = userService.createUser(userMapper.toEntity(userDTO));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}").build(user.getId());
        return ResponseEntity.created(location).body(userMapper.toDTO(user));
    }

    @GetMapping(path = "current")
    public ResponseEntity<UserDTO> getCurrentUser() {
        return getUserById(2);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id) {
        return ResponseEntity.of(userService.getUser(id).map(userMapper::toDTO));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(
                userMapper.toDTO(
                        userService.updateUser(userMapper.toEntity(userDTO))
                )
        );
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

}
