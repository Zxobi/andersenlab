package com.andersenlab.bookstore.bookservice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTPayloadDTO {

    private UserDTO user;
    private List<String> authorities;

}
