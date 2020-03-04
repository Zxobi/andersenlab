package com.andersenlab.bookstore.userservice.controller;

import com.andersenlab.bookstore.userservice.model.User;
import com.andersenlab.bookstore.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
        public ResponseEntity<List<User>> getUsers(@RequestParam(value = "userId", required = false) Integer[] ids) {
        return ResponseEntity.ok(
                ids == null ? userService.getUsers() : userService.getUsersById(Arrays.asList(ids))
        );
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userService.createUser(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}").build(user.getId());
        return ResponseEntity.created(location).body(user);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return ResponseEntity.of(userService.getUser(id));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @DeleteMapping
    public ResponseEntity<User> deleteUser(@RequestBody User user) {
        userService.deleteUser(user);
        return ResponseEntity.noContent().build();
    }


}
