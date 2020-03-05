package com.andersenlab.bookstore.bookservice.service.impl;

import com.andersenlab.bookstore.bookservice.model.Book;
import com.andersenlab.bookstore.bookservice.repository.BookRepository;
import com.andersenlab.bookstore.bookservice.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getBooksById(Iterable<Integer> ids) {
        return bookRepository.findAllById(ids);
    }

    @Override
    public Optional<Book> getBook(int id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book updateBook(Book book) {
        return bookRepository.saveAndFlush(book);
    }

    @Override
    public Book createBook(Book book) {
        return bookRepository.saveAndFlush(book);
    }

    @Override
    public void deleteBookById(int id) {
        bookRepository.deleteById(id);
    }

}
