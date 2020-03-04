package com.andersenlab.bookstore.bookservice.service;

import com.andersenlab.bookstore.bookservice.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> getBooks();
    List<Book> getBooksById(Iterable<Integer> ids);
    Optional<Book> getBook(int id);

    Book updateBook(Book book);
    Book createBook(Book book);
    void deleteBook(Book book);

}
