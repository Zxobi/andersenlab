package com.andersenlab.patternsSample.entity;

public class Book extends Literature {

    private String author;

    public Book(BookBuilder builder) {
        super(builder);
        this.author = builder.author;
    }

    public String getAuthor() {
        return author;
    }

    public static BookBuilder newBuilder() {
        return new BookBuilder();
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publishingHouse='" + publishingHouse.getName() + '\'' +
                ", publishDate=" + publishDate +
                '}';
    }
}
