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
        Book.Builder builder = Book.newBuilder();
        builder.setTitle(title)
                .setAuthor(author)
                .setPublishingHouse(this)
                .setPublishDate(new Date());

        Book book = builder.build();
        books.add(book);
        return book;
    }

    @Override
    public String toString() {
        return name;
    }
}
