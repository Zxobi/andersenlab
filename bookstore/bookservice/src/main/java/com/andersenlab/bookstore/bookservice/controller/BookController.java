package com.andersenlab.bookstore.bookservice.controller;

import com.andersenlab.bookstore.bookservice.model.Book;
import com.andersenlab.bookstore.bookservice.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getBooks(@RequestParam(value = "bookId", required = false) Integer[] ids) {
        return ResponseEntity.ok(
                ids == null ? bookService.getBooks() : bookService.getBooksById(Arrays.asList(ids))
        );
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        bookService.createBook(book);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}").build(book.getId());
        return ResponseEntity.created(location).body(book);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        return ResponseEntity.of(bookService.getBook(id));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.updateBook(book));
    }

    @DeleteMapping
    public ResponseEntity<Book> deleteBook(@RequestBody Book book) {
        bookService.deleteBook(book);
        return ResponseEntity.noContent().build();
    }

}