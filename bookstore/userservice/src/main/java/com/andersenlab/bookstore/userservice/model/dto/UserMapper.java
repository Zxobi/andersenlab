package com.andersenlab.bookstore.userservice.model.dto;

import com.andersenlab.bookstore.userservice.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserDTO userDTO) {
        return new User(
                userDTO.getId(),
                userDTO.getUsername()
        );
    }

    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername()
        );
    }

}
