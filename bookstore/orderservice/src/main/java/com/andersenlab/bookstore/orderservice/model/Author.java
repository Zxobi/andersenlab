package com.andersenlab.bookstore.orderservice.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Author {

    private int id;
    private String fullName;
    private List<Book> books;

    public Author() {

    }

}
