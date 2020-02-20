package com.andersenlab.patternsSample.entity;

public class BookBuilder extends LiteratureBuilder<Book, BookBuilder> {
    protected String author;

    public BookBuilder setAuthor(String author) {
        this.author = author;
        return this;
    }

    @Override
    public Book build() {
        return new Book(this);
    }
}
