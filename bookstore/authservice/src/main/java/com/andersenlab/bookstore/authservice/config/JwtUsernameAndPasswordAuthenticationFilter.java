package com.andersenlab.bookstore.authservice.config;

import com.andersenlab.bookstore.authservice.common.dto.UserDTO;
import com.andersenlab.bookstore.authservice.common.dto.UserDetailsDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Request;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

@Slf4j
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtConfig jwtConfig;
    private final AuthenticationManager authenticationManager;

    public JwtUsernameAndPasswordAuthenticationFilter(JwtConfig jwtConfig, AuthenticationManager authenticationManager) {
        this.jwtConfig = jwtConfig;
        this.authenticationManager = authenticationManager;

        this.setRequiresAuthenticationRequestMatcher(
                new AntPathRequestMatcher(jwtConfig.getUri(), Request.HttpMethod.POST.toString())
        );
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserCredentials userCredentials = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userCredentials.getUsername(), userCredentials.getPassword(), Collections.emptyList());

            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new AuthenticationCredentialsNotFoundException("Cannot read credentials from request", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException {
        log.info("Authentication successful with authResult: " + authResult);

        ObjectMapper objectMapper = new ObjectMapper();
        UserDetailsDTO userDetailsDTO = ((UserDetailsWrapper)authResult.getPrincipal()).getUserDetails();
        long now = System.currentTimeMillis();
        String token = JWT.create()
                .withSubject(authResult.getName())
                .withIssuedAt(new Date(now))
                .withExpiresAt(new Date(now + jwtConfig.getExpiration() * 1000))
                .withClaim("user", objectMapper.writeValueAsString(
                        new UserDTO(
                                userDetailsDTO.getId(),
                                userDetailsDTO.getUsername()
                        )
                ))
                .withArrayClaim(
                        "authorities",
                        authResult.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority).toArray(String[]::new))
                .sign(Algorithm.HMAC256(jwtConfig.getSecret()));

        response.addHeader(jwtConfig.getHeader(), token);
    }

    @Getter
    private static class UserCredentials {
        private String username;
        private String password;
    }

}
