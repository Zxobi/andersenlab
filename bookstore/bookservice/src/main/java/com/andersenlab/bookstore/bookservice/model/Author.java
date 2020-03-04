package com.andersenlab.bookstore.bookservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "authors")
@AllArgsConstructor
@Getter
@Setter
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "fullName", nullable = false)
    private String fullName;

    @ManyToMany(mappedBy = "authors")
    @JsonBackReference
    private List<Book> books;

    public Author() {

    }

}
