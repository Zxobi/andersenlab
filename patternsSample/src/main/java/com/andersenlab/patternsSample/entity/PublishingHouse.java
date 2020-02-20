package com.andersenlab.patternsSample.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PublishingHouse {

    private long id;
    private String name;

    private List<Literature> literatures = new ArrayList<>();

    public PublishingHouse(String name) {
        this.name = name;
    }

    public PublishingHouse(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        return "PublishingHouse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
