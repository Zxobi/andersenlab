package com.andersenlab.patternsSample.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PublishingHouse {

    private String name;

    private List<Literature> literatures = new ArrayList<>();

    public PublishingHouse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Literature> getLiteratures() {
        return literatures;
    }

    public Book publishBook(String title, String author) {
        BookBuilder builder = Book.newBuilder();
        builder.setTitle(title)
                .setAuthor(author)
                .setPublishingHouse(this)
                .setPublishDate(new Date());

        Book book = builder.build();
        literatures.add(book);
        return book;
    }

    public Magazine publishMagazine(String title, int serialNumber) {
        MagazineBuilder builder = Magazine.newBuilder();
        builder.setTitle(title)
                .setSerialNumber(serialNumber)
                .setPublishingHouse(this)
                .setPublishDate(new Date());

        Magazine magazine = builder.build();
        literatures.add(magazine);
        return magazine;
    }

    @Override
    public String toString() {
        return name;
    }
}
