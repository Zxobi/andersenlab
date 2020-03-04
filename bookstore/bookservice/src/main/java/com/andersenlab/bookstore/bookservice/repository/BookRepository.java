package com.andersenlab.bookstore.bookservice.repository;

import com.andersenlab.bookstore.bookservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

}
