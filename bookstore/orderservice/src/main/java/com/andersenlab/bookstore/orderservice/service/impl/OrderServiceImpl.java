package com.andersenlab.bookstore.orderservice.service.impl;

import com.andersenlab.bookstore.orderservice.model.BookOrder;
import com.andersenlab.bookstore.orderservice.model.Order;
import com.andersenlab.bookstore.orderservice.model.dto.BookDTO;
import com.andersenlab.bookstore.orderservice.model.dto.UserDTO;
import com.andersenlab.bookstore.orderservice.repository.OrderRepository;
import com.andersenlab.bookstore.orderservice.service.BookService;
import com.andersenlab.bookstore.orderservice.service.OrderService;
import com.andersenlab.bookstore.orderservice.service.UserService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final UserService userService;
    private final BookService bookService;

    private final RestTemplate restTemplate;

    public OrderServiceImpl(BookService bookService, OrderRepository orderRepository, UserService userService, RestTemplate restTemplate) {
        this.bookService = bookService;
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(int id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order createOrder(List<BookOrder> bookOrders) {
        Map<Integer, Integer> bookIdToCountMap = new HashMap<>();
        for (BookOrder bookOrder : bookOrders) {
            bookIdToCountMap.put(bookOrder.getBookId(), bookOrder.getCount());
        }

        List<BookDTO> bookDTOs = bookService.getBooksById(bookIdToCountMap.keySet());
        float totalPrice = bookDTOs.stream()
                .map(bookDTO -> bookDTO.getPrice() * bookIdToCountMap.get(bookDTO.getId()))
                .reduce(0f, Float::sum);

        UserDTO user = getCurrentUser();

        Order order = new Order(0, totalPrice, new Date(), user.getId(), bookOrders);

        return orderRepository.saveAndFlush(order);
    }

    private UserDTO getCurrentUser() {
        return userService.getCurrentUser();
    }

}
