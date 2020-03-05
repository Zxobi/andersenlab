package com.andersenlab.bookstore.userservice.service.impl;

import com.andersenlab.bookstore.userservice.model.User;
import com.andersenlab.bookstore.userservice.repository.UserRepository;
import com.andersenlab.bookstore.userservice.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUser(int userId) {
        return userRepository.findById(userId);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersById(Iterable<Integer> ids) {
        return userRepository.findAllById(ids);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

}
