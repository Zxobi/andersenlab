package com.andersenlab.bookstorageweb.service;

import com.andersenlab.bookstorageweb.entity.Role;
import com.andersenlab.bookstorageweb.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;
import java.util.Set;

public interface UserService extends UserDetailsService {

    Collection<User> findAll();

    User createUser(String username, String password, Set<Role> roles);

}
