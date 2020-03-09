package com.andersenlab.bookstore.bookservice.common.mapper;

import com.andersenlab.bookstore.bookservice.common.dto.AuthorDTO;
import com.andersenlab.bookstore.bookservice.model.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    public Author toEntity(AuthorDTO authorDTO) {
        return new Author(
                authorDTO.getId(),
                authorDTO.getFullName(),
                null
        );
    }

    public AuthorDTO toDTO(Author author) {
        return new AuthorDTO(
                author.getId(),
                author.getFullName()
        );
    }

}
