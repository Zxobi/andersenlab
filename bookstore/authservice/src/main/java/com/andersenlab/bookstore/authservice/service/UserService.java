package com.andersenlab.bookstore.authservice.service;

import com.andersenlab.bookstore.authservice.common.dto.UserDetailsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@FeignClient("user-service")
public interface UserService {

    @GetMapping("/users")
    List<UserDetailsDTO> getUserDetails(@RequestParam String username);

}
