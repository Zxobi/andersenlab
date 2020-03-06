package com.andersenlab.bookstore.userservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDTO {

    private int id;
    private String username;
    private String password;
    private List<String> authorities;

}
