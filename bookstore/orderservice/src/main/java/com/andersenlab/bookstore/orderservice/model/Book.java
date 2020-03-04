package com.andersenlab.bookstore.orderservice.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Book {

    private int id;
    private String title;
    private float price;
    private int quantity;
    private List<Author> authors;

    public Book() {

    }

}
