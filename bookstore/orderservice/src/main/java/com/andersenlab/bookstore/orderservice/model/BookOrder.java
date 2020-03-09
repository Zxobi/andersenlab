package com.andersenlab.bookstore.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookOrder {

    @Column(name = "book_id", nullable = false, updatable = false)
    private int bookId;
    @Column(name = "count", nullable = false, updatable = false)
    private int count;

}
