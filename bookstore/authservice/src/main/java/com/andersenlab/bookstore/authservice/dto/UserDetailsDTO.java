package com.andersenlab.bookstore.authservice.dto;

import lombok.*;

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
