package com.andersenlab.patternsSample.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PublishingHouse {

    private String name;

    private List<Book> books = new ArrayList<>();

    public PublishingHouse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public Book publishBook(String title, String author) {
        Book.Builder builder = new Book.Builder();
        builder.setTitle(title);
        builder.setAuthor(author);
        builder.setPublishingHouse(this);
        builder.setPublishDate(new Date());

        Book book = builder.getBook();
        books.add(book);
        return book;
    }

    @Override
    public String toString() {
        return name;
    }
}
