package com.andersenlab.bookstore.authservice.service;

import com.andersenlab.bookstore.authservice.dto.UserDetailsDTO;
import com.andersenlab.bookstore.authservice.dto.UserDetailsWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<UserDetailsDTO> userDetailsDTOs = userService.getUserDetails(s);

        log.info("Get user details: " + userDetailsDTOs);

        if (userDetailsDTOs == null || userDetailsDTOs.isEmpty()) {
            throw new UsernameNotFoundException(s);
        }

        return new UserDetailsWrapper(userDetailsDTOs.get(0));
    }

}
