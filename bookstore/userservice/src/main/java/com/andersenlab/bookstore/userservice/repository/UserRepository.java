package com.andersenlab.bookstore.userservice.repository;

import com.andersenlab.bookstore.userservice.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDetails, Integer> {

    Optional<UserDetails> getUserDetailsByUsername(String username);

}
