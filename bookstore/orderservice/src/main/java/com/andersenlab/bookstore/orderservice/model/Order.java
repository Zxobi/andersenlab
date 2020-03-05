package com.andersenlab.bookstore.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "total_price")
    private float totalPrice;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "user_id")
    private int userId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "order_books",
            joinColumns = @JoinColumn(name = "order_id")
    )
    @Column(name = "book_id")
    private List<BookOrder> books;

}