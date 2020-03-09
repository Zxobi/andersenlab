package com.andersenlab.bookstore.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "total_price", nullable = false, updatable = false)
    private Float totalPrice;

    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createdDate;

    @Column(name = "user_id", nullable = false, updatable = false)
    private Integer userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "order_books",
            joinColumns = @JoinColumn(name = "order_id")
    )
    @Column(name = "book_id", updatable = false)
    private List<BookOrder> books;

    @PrePersist
    private void setCreatedDate() {
        createdDate = new Date();
    }

}