package com.andersenlab.bookstorageweb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Book extends Literature {

    @Column(name = "author")
    private String author;

    protected Book() {

    }

    public Book(Builder builder) {
        super(builder);
        this.author = builder.author;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public static class Builder extends Literature.Builder<Builder> {

        String author;

        public Builder() {

        }

        public Builder setAuthor(String author) {
            this.author = author;
            return self();
        }

        @Override
        public Book build() {
            return new Book(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
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
