package com.andersenlab.bookstore.authservice.controller;

import com.andersenlab.bookstore.authservice.common.dto.JWTPayloadDTO;
import com.andersenlab.bookstore.authservice.common.dto.UserDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/jwt")
public class JWTController {

    @PostMapping("/validate")
    public ResponseEntity<JWTPayloadDTO> validate(@RequestBody String token) throws JsonProcessingException {
        if (token == null) {
            return ResponseEntity.badRequest().build();
        }

        DecodedJWT decodedJWT = JWT.decode(token);
        return ResponseEntity.ok(new JWTPayloadDTO(
                    new ObjectMapper().readValue(decodedJWT.getClaim("user").asString(), UserDTO.class),
                    decodedJWT.getClaim("authorities").asList(String.class)
                )
        );
    }

}
