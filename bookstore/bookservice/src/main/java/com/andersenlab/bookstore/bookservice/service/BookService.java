package com.andersenlab.bookstore.bookservice.service;

import com.andersenlab.bookstore.bookservice.common.dto.BookOrderDTO;
import com.andersenlab.bookstore.bookservice.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<BookOrderDTO> reserveBooks(List<BookOrderDTO> bookOrders);

    List<Book> getBooks();
    List<Book> getBooksById(Iterable<Integer> ids);
    Optional<Book> getBook(int id);

    Book updateBook(Book book);
    Book createBook(Book book);
    void deleteBookById(int id);

}
