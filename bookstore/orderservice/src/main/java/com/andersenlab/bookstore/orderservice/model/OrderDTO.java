package com.andersenlab.bookstore.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class OrderDTO {

    private int id;
    private float totalPrice;
    private Date createdDate;
    private User user;
    private List<Book> books;

}
