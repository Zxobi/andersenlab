package com.andersenlab.bookstore.orderservice.service;

import com.andersenlab.bookstore.orderservice.model.BookOrder;
import com.andersenlab.bookstore.orderservice.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<Order> getOrders();
    Optional<Order> getOrderById(int id);

    Order createOrder(List<BookOrder> bookOrderMap);

}
