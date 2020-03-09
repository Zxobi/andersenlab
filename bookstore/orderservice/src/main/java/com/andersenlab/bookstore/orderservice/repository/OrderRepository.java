package com.andersenlab.bookstore.orderservice.repository;

import com.andersenlab.bookstore.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT o FROM Order o JOIN FETCH o.books")
    List<Order> findAll();

    List<Order> findOrdersByUserId(int userId);

}
