package com.andersenlab.bookstore.userservice.model.dto;

import com.andersenlab.bookstore.userservice.model.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsMapper {

    public UserDTO toUserDTO(UserDetails userDetails) {
        return new UserDTO(
                userDetails.getId(),
                userDetails.getUsername()
        );
    }

    public UserDetailsDTO toUserDetailsDTO(UserDetails userDetails) {
        return new UserDetailsDTO(
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
    }

    public UserDetails toEntity(UserDTO userDTO) {
        UserDetails userDetails = new UserDetails();
        userDetails.setId(userDTO.getId());
        userDetails.setUsername(userDTO.getUsername());
        return userDetails;
    }

    public UserDetails toEntity(UserDetailsDTO userDetailsDTO) {
        return new UserDetails(
                userDetailsDTO.getId(),
                userDetailsDTO.getUsername(),
                userDetailsDTO.getPassword(),
                userDetailsDTO.getAuthorities()
        );
    }

}
