package com.andersenlab.patternsSample.entity;

import java.util.Date;

public class Book {

    private String title;
    private String author;
    private PublishingHouse publishingHouse;
    private Date publishDate;

    public Book() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public PublishingHouse getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(PublishingHouse publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    static class Builder {
        private Book book = new Book();

        public Book getBook() {
            return book;
        }

        public void setTitle(String title) {
            book.setTitle(title);
        }

        public void setAuthor(String author) {
            book.setAuthor(author);
        }

        public void setPublishingHouse(PublishingHouse publishingHouse) {
            book.setPublishingHouse(publishingHouse);
        }

        public void setPublishDate(Date publishDate) {
            book.setPublishDate(publishDate);
        }
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publishingHouse='" + publishingHouse.getName() + '\'' +
                ", publishDate=" + publishDate +
                '}';
    }
}
