package com.andersenlab.bookstore.bookservice.controller;

import com.andersenlab.bookstore.bookservice.common.Authorities;
import com.andersenlab.bookstore.bookservice.common.dto.BookOrderDTO;
import com.andersenlab.bookstore.bookservice.common.dto.JWTPayloadDTO;
import com.andersenlab.bookstore.bookservice.model.Book;
import com.andersenlab.bookstore.bookservice.common.dto.BookDTO;
import com.andersenlab.bookstore.bookservice.common.mapper.BookMapper;
import com.andersenlab.bookstore.bookservice.service.AuthService;
import com.andersenlab.bookstore.bookservice.service.BookService;
import org.springframework.http.HttpStatus;
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
    private final AuthService authService;

    private final BookMapper bookMapper;

    public BookController(BookService bookService, AuthService authService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.authService = authService;
        this.bookMapper = bookMapper;
    }

    @RequestMapping("/books/reserve")
    public ResponseEntity<List<BookOrderDTO>> reserveBooks(@RequestBody List<BookOrderDTO> bookOrders) {
        return ResponseEntity.ok(bookService.reserveBooks(bookOrders));
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
    public ResponseEntity<BookDTO> createBook(@RequestHeader("JWT-token") String authToken, @RequestBody BookDTO bookDTO) {
        JWTPayloadDTO jwtPayload;
        if ((jwtPayload = validateAuthToken(authToken)) == null
                || !jwtPayload.getAuthorities().contains(Authorities.ADMIN.name())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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
    public ResponseEntity<BookDTO> updateBook(@RequestHeader("JWT-token") String authToken, @RequestBody BookDTO book) {
        JWTPayloadDTO jwtPayload;
        if ((jwtPayload = validateAuthToken(authToken)) == null
                || !jwtPayload.getAuthorities().contains(Authorities.ADMIN.name())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(
                bookMapper.toDTO(
                        bookService.updateBook(bookMapper.toEntity(book))
                )
        );
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<BookDTO> deleteBook(@RequestHeader("JWT-token") String authToken, @PathVariable int id) {
        JWTPayloadDTO jwtPayload;
        if ((jwtPayload = validateAuthToken(authToken)) == null
                || !jwtPayload.getAuthorities().contains(Authorities.ADMIN.name())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        bookService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }

    private JWTPayloadDTO validateAuthToken(String authToken) {
        if (authToken == null || authToken.isEmpty()) {
            return null;
        }

        return authService.validate(authToken);
    }

}