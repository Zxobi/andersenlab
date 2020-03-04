package com.andersenlab.bookstore.userservice.repository;

import com.andersenlab.bookstore.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
