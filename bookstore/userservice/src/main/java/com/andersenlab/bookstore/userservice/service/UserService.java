package com.andersenlab.bookstore.userservice.service;

import com.andersenlab.bookstore.userservice.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> getUser(int userId);
    List<User> getUsers();
    List<User> getUsersById(Iterable<Integer> ids);

    User updateUser(User user);
    User createUser(User user);
    void deleteUserById(int id);


}
