package com.andersenlab.bookstore.userservice.common.mapper;

import com.andersenlab.bookstore.userservice.common.dto.UserDTO;
import com.andersenlab.bookstore.userservice.common.dto.UserDetailsDTO;
import com.andersenlab.bookstore.userservice.model.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsMapper {

    public UserDTO toUserDTO(UserDetails userDetails) {
        return new UserDTO(
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getAuthorities()
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
        return new UserDetails(
                userDTO.getId(),
                userDTO.getUsername(),
                null,
                userDTO.getAuthorities()
        );
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
