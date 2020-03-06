package com.andersenlab.bookstore.userservice.service.impl;

import com.andersenlab.bookstore.userservice.model.UserDetails;
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
    public Optional<UserDetails> getUser(int userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<UserDetails> getUserByUsername(String username) {
        return userRepository.getUserDetailsByUsername(username);
    }

    @Override
    public List<UserDetails> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<UserDetails> getUsersById(Iterable<Integer> ids) {
        return userRepository.findAllById(ids);
    }

    @Override
    public UserDetails updateUser(UserDetails user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetails createUser(UserDetails user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

}
