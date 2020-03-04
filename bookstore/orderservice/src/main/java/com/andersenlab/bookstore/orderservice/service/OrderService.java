package com.andersenlab.bookstore.orderservice.service;

import com.andersenlab.bookstore.orderservice.model.Book;
import com.andersenlab.bookstore.orderservice.model.OrderDTO;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<OrderDTO> getOrders();
    Optional<OrderDTO> getOrderById(int id);

    OrderDTO createOrder(int userId, List<Book> books);

}
