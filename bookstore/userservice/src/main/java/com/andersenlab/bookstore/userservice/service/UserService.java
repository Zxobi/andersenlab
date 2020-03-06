package com.andersenlab.bookstore.userservice.service;

import com.andersenlab.bookstore.userservice.model.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserDetails> getUser(int userId);
    Optional<UserDetails> getUserByUsername(String username);
    List<UserDetails> getUsers();
    List<UserDetails> getUsersById(Iterable<Integer> ids);

    UserDetails updateUser(UserDetails user);
    UserDetails createUser(UserDetails user);
    void deleteUserById(int id);


}
