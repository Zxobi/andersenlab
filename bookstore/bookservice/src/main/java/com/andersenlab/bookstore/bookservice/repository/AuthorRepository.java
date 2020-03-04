package com.andersenlab.bookstore.bookservice.repository;

import com.andersenlab.bookstore.bookservice.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

}
