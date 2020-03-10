package com.andersenlab.bookstore.orderservice.service.impl;

import com.andersenlab.bookstore.orderservice.common.dto.BookDTO;
import com.andersenlab.bookstore.orderservice.common.dto.BookOrderDTO;
import com.andersenlab.bookstore.orderservice.common.dto.OrderDTO;
import com.andersenlab.bookstore.orderservice.common.dto.UserDTO;
import com.andersenlab.bookstore.orderservice.common.mapper.BookOrderMapper;
import com.andersenlab.bookstore.orderservice.common.mapper.OrderMapper;
import com.andersenlab.bookstore.orderservice.model.BookOrder;
import com.andersenlab.bookstore.orderservice.model.Order;
import com.andersenlab.bookstore.orderservice.model.OrderStatus;
import com.andersenlab.bookstore.orderservice.repository.OrderRepository;
import com.andersenlab.bookstore.orderservice.service.BookService;
import com.andersenlab.bookstore.orderservice.service.OrderService;
import com.andersenlab.bookstore.orderservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final UserService userService;
    private final BookService bookService;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final OrderMapper orderMapper;
    private final BookOrderMapper bookOrderMapper;

    public OrderServiceImpl(BookService bookService, OrderRepository orderRepository, UserService userService,
                            KafkaTemplate<String, Object> kafkaTemplate, OrderMapper orderMapper, BookOrderMapper bookOrderMapper) {
        this.bookService = bookService;
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.kafkaTemplate = kafkaTemplate;
        this.orderMapper = orderMapper;
        this.bookOrderMapper = bookOrderMapper;
    }

    @Override
    public List<OrderDTO> getOrdersForUser(UserDTO user) {
        List<Order> orders = orderRepository.findOrdersByUserId(user.getId());
        if (orders.isEmpty()) {
            return Collections.emptyList();
        }

        List<BookDTO> books = getOrdersBooks(orders);

        return orders.stream().map(order -> orderMapper.toDTO(order, user, books)).collect(Collectors.toList());
    }

    @Override
    public Optional<OrderDTO> getOrderById(int id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        return orderOptional.map(order -> {
            UserDTO user = userService.getUser(order.getUserId());
            List<BookDTO> books = getOrderBooks(order);

            return orderMapper.toDTO(order, user, books);
        });
    }

    @Transactional
    @Override
    public OrderDTO createOrder(List<BookOrderDTO> bookOrdersDTO, UserDTO user) {
        bookOrdersDTO = bookService.reserveBooks(bookOrdersDTO);

        float totalPrice = bookOrdersDTO.stream().map(bookOrder -> bookOrder.getBook().getPrice() * bookOrder.getCount())
                .reduce(0f, Float::sum);
        List<BookOrder> bookOrders = bookOrdersDTO.stream().map(bookOrderMapper::toEntity).collect(Collectors.toList());

        Order order = new Order(null, totalPrice, null, user.getId(), OrderStatus.CREATED, bookOrders);
        OrderDTO orderDTO = orderMapper.toDTO(
                orderRepository.save(order),
                user,
                bookOrdersDTO.stream().map(BookOrderDTO::getBook).collect(Collectors.toList())
        );

        try {
            SendResult<String, Object> sendResult = kafkaTemplate.send("newOrder", orderDTO).get();
            log.info("Result sent:'{}'", sendResult);
        } catch (ExecutionException | InterruptedException ex) {
            log.error("Could not send kafka message:'{}'", ex.getMessage());
            throw new RuntimeException(ex);
        }

        return orderDTO;
    }

    @Transactional
    @KafkaListener(topics = "orderPaid", groupId = "${kafka.topic.orderServiceGroupId}")
    public void listenOrderPaid(String orderIdString) {
        log.info("ReceivedOrderPaid='{}'", orderIdString);

        Integer orderId = Integer.valueOf(orderIdString);
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            throw new RuntimeException("Order paid listener not found order with id=" + orderId);
        }

        Order order = orderOptional.get();
        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);
    }

    private List<BookDTO> getOrderBooks(Order order) {
        Set<Integer> bookIds = order.getBooks().stream().map(BookOrder::getBookId).collect(Collectors.toSet());

        return bookService.getBooksById(bookIds);
    }

    private List<BookDTO> getOrdersBooks(List<Order> orders) {
        Set<Integer> bookIds = orders.stream().flatMap(
                order -> order.getBooks().stream().map(BookOrder::getBookId)
        ).collect(Collectors.toSet());

        return bookService.getBooksById(bookIds);
    }

}
