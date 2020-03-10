package com.andersenlab.bookstore.userservice.controller;

import com.andersenlab.bookstore.userservice.common.dto.JWTPayloadDTO;
import com.andersenlab.bookstore.userservice.common.dto.UserDTO;
import com.andersenlab.bookstore.userservice.common.dto.UserDetailsDTO;
import com.andersenlab.bookstore.userservice.common.mapper.UserDetailsMapper;
import com.andersenlab.bookstore.userservice.model.UserDetails;
import com.andersenlab.bookstore.userservice.service.AuthService;
import com.andersenlab.bookstore.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    private final UserDetailsMapper userDetailsMapper;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, AuthService authService, UserDetailsMapper userDetailsMapper, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authService = authService;
        this.userDetailsMapper = userDetailsMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<List<UserDetailsDTO>> getUsers(
            @RequestParam(value = "userId", required = false) List<Integer> ids,
            @RequestParam(value = "username", required = false) String username
    ) {
        if (username != null) {
            return ResponseEntity.of(userService.getUserByUsername(username)
                    .map(user -> List.of(userDetailsMapper.toUserDetailsDTO(user))));
        }
        List<UserDetails> users = ids == null
                ? userService.getUsers()
                : userService.getUsersById(ids);

        return ResponseEntity.ok(
                users.stream().map(userDetailsMapper::toUserDetailsDTO).collect(Collectors.toList())
        );
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDetailsDTO userDetailsDTO) {
        if (userDetailsDTO.getId() != 0 || userDetailsDTO.getPassword() == null || userDetailsDTO.getUsername() == null) {
            return ResponseEntity.badRequest().build();
        }

        UserDetails user = userDetailsMapper.toEntity(userDetailsDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userService.createUser(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}").build(user.getId());
        return ResponseEntity.created(location).body(userDetailsMapper.toUserDTO(user));
    }

    @GetMapping(path = "current")
    public ResponseEntity<UserDTO> getCurrentUser(@RequestHeader("JWT-token") String authToken) {
        JWTPayloadDTO jwtPayloadDTO = validateAuthToken(authToken);
        if (jwtPayloadDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.of(
                userService.getUser(jwtPayloadDTO.getUser().getId()).map(userDetailsMapper::toUserDTO)
        );
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id) {
        return ResponseEntity.of(userService.getUser(id).map(userDetailsMapper::toUserDTO));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(
                userDetailsMapper.toUserDTO(
                        userService.updateUser(userDetailsMapper.toEntity(userDTO))
                )
        );
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    private JWTPayloadDTO validateAuthToken(String authToken) {
        if (authToken == null || authToken.isEmpty()) {
            return null;
        }

        return authService.validate(authToken);
    }

}
