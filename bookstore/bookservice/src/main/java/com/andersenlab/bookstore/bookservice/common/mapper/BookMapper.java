package com.andersenlab.bookstore.bookservice.common.mapper;

import com.andersenlab.bookstore.bookservice.common.dto.BookDTO;
import com.andersenlab.bookstore.bookservice.model.Book;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
public class BookMapper {

    private final AuthorMapper authorMapper;

    public BookMapper(AuthorMapper authorMapper) {
        this.authorMapper = authorMapper;
    }


    public Book toEntity(BookDTO bookDTO) {
        return new Book(
                bookDTO.getId(),
                bookDTO.getTitle(),
                bookDTO.getPrice(),
                -1,
                bookDTO.getAuthors().stream().map(authorMapper::toEntity).collect(Collectors.toList())
        );
    }

    public BookDTO toDTO(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getPrice(),
                book.getAuthors().stream().map(authorMapper::toDTO).collect(Collectors.toList())
        );
    }


}
