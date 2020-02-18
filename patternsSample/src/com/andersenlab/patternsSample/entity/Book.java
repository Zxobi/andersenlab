package com.andersenlab.patternsSample.entity;

import java.util.Date;

public class Book {

    private String title;
    private String author;
    private PublishingHouse publishingHouse;
    private Date publishDate;

    public Book() {}

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

    public static Builder newBuilder() {
        return new Book().new Builder();
    }

    public class Builder {
        public Builder setTitle(String title) {
            Book.this.setTitle(title);
            return this;
        }

        public Builder setAuthor(String author) {
            Book.this.setAuthor(author);
            return this;
        }

        public Builder setPublishingHouse(PublishingHouse publishingHouse) {
            Book.this.setPublishingHouse(publishingHouse);
            return this;
        }

        public Builder setPublishDate(Date publishDate) {
            Book.this.setPublishDate(publishDate);
            return this;
        }

        public Book build() {
            return Book.this;
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
