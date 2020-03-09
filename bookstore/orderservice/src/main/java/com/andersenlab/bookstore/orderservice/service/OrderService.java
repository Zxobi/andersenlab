package com.andersenlab.bookstore.orderservice.service;

import com.andersenlab.bookstore.orderservice.common.dto.BookOrderDTO;
import com.andersenlab.bookstore.orderservice.common.dto.OrderDTO;
import com.andersenlab.bookstore.orderservice.common.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Optional<OrderDTO> getOrderById(int id);
    List<OrderDTO> getOrdersForUser(UserDTO userDTO);

    OrderDTO createOrder(List<BookOrderDTO> bookOrderMap, UserDTO user);

}
