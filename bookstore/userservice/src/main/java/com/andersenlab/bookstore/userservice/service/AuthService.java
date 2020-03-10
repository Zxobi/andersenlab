package com.andersenlab.bookstore.userservice.service;

import com.andersenlab.bookstore.userservice.common.dto.JWTPayloadDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("auth-service")
public interface AuthService {

    @RequestMapping("/auth/jwt/validate")
    JWTPayloadDTO validate(@RequestBody String token);

}
