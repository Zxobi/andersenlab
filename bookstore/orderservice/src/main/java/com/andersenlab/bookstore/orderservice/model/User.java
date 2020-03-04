package com.andersenlab.bookstore.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class User {

    private int id;
    private String username;

    public User() {
        
    }

}
