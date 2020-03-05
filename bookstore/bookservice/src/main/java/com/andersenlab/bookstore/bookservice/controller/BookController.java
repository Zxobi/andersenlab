package com.andersenlab.bookstore.bookservice.controller;

import com.andersenlab.bookstore.bookservice.model.Book;
import com.andersenlab.bookstore.bookservice.model.dto.BookDTO;
import com.andersenlab.bookstore.bookservice.model.dto.BookMapper;
import com.andersenlab.bookstore.bookservice.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getBooks(@RequestParam(value = "bookId", required = false) Integer[] ids) {
        List<Book> books = ids == null
                ? bookService.getBooks()
                : bookService.getBooksById(Arrays.asList(ids));
        return ResponseEntity.ok(
                books.stream().map(bookMapper::toDTO).collect(Collectors.toList())
        );
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        if (bookDTO.getId() != 0) {
            return ResponseEntity.badRequest().build();
        }

        Book book = bookService.createBook(bookMapper.toEntity(bookDTO));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}").build(book.getId());
        return ResponseEntity.created(location).body(bookMapper.toDTO(book));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable int id) {
        return ResponseEntity.of(
                bookService.getBook(id).map(bookMapper::toDTO)
        );
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<BookDTO> updateBook(@RequestBody BookDTO book) {
        return ResponseEntity.ok(
                bookMapper.toDTO(
                        bookService.updateBook(bookMapper.toEntity(book))
                )
        );
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<BookDTO> deleteBook(@PathVariable int id) {
        bookService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }

}