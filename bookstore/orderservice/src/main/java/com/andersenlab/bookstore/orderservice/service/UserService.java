package com.andersenlab.bookstore.orderservice.service;

import com.andersenlab.bookstore.orderservice.model.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("user-service")
public interface UserService {

    @RequestMapping("/users/{id}")
    UserDTO getUser(@PathVariable int id);

    @RequestMapping("/users/current")
    UserDTO getCurrentUser();

}
