package com.andersenlab.bookstorageweb.repository;

import com.andersenlab.bookstorageweb.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface UserRepository extends CrudRepository<User, Long> {

    Collection<User> findAll();
    User findByUsername(String username);

}
