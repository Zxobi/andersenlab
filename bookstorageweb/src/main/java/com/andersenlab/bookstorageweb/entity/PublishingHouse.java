package com.andersenlab.bookstorageweb.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PublishingHouse")
public class PublishingHouse {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, updatable = false)
    private long id;
    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "publishingHouse", cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private List<Literature> literatures = new ArrayList<>();

    public PublishingHouse() {

    }

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

    public void setName(String name) {
        this.name = name;
    }

    public List<Literature> getLiteratures() {
        return literatures;
    }

    public Book publishBook(String title, String author) {
        Book book = new Book.Builder()
                .setTitle(title)
                .setAuthor(author)
                .setPublishingHouse(this)
                .setPublishDate(new Date())
                .build();

        literatures.add(book);
        return book;
    }

    public Magazine publishMagazine(String title, int serialNumber) {
        Magazine magazine = new Magazine.Builder()
                .setTitle(title)
                .setSerialNumber(serialNumber)
                .setPublishingHouse(this)
                .setPublishDate(new Date())
                .build();

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
