package com.andersenlab.bookstore.orderservice.service.impl;

import com.andersenlab.bookstore.orderservice.model.BookOrder;
import com.andersenlab.bookstore.orderservice.model.Order;
import com.andersenlab.bookstore.orderservice.model.dto.BookDTO;
import com.andersenlab.bookstore.orderservice.model.dto.UserDTO;
import com.andersenlab.bookstore.orderservice.repository.OrderRepository;
import com.andersenlab.bookstore.orderservice.service.OrderService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static final String URL_BASE_USER_SERVICE = "http://localhost:9103";
    private static final String URL_BASE_BOOK_SERVICE = "http://localhost:9101";

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    public OrderServiceImpl(OrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
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

        List<BookDTO> bookDTOs = getBooksById(bookIdToCountMap.keySet());
        float totalPrice = bookDTOs.stream()
                .map(bookDTO -> bookDTO.getPrice() * bookIdToCountMap.get(bookDTO.getId()))
                .reduce(0f, Float::sum);

        UserDTO user = getCurrentUser();

        Order order = new Order(0, totalPrice, new Date(), user.getId(), bookOrders);
;
        return orderRepository.saveAndFlush(order);
    }

    private UserDTO getCurrentUser() {
        return restTemplate.getForEntity(URL_BASE_USER_SERVICE + "/users/current", UserDTO.class).getBody();
    }

    @Override
    public List<BookDTO> getOrderBooks(Order order) {
        return getBooksById(order.getBooks().stream().map(BookOrder::getBookId).collect(Collectors.toList()));
    }

    @Override
    public UserDTO getOrderUser(Order order) {
        return restTemplate.getForEntity(URL_BASE_USER_SERVICE + "/users/" + order.getUserId(), UserDTO.class).getBody();
    }

    private List<BookDTO> getBooksById(Iterable<Integer> ids) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(URL_BASE_BOOK_SERVICE + "/books");
        ids.forEach(bookId -> uriBuilder.queryParam("bookId", bookId));
        return restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<BookDTO>>() {}).getBody();
    }

}
