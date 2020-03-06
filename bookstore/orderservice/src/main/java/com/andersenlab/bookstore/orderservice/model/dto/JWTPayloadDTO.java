package com.andersenlab.bookstore.orderservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTPayloadDTO {

    private int userId;
    private String username;
    private List<String> authorities;

}
