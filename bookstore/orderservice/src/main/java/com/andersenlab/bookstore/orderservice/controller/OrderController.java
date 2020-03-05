package com.andersenlab.bookstore.orderservice.controller;

import com.andersenlab.bookstore.orderservice.model.BookOrder;
import com.andersenlab.bookstore.orderservice.model.Order;
import com.andersenlab.bookstore.orderservice.model.dto.*;
import com.andersenlab.bookstore.orderservice.service.BookService;
import com.andersenlab.bookstore.orderservice.service.OrderService;
import com.andersenlab.bookstore.orderservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final BookService bookService;
    private final OrderMapper orderMapper;
    private final BookOrderMapper bookOrderMapper;

    public OrderController(OrderService orderService, UserService userService, BookService bookService, OrderMapper orderMapper, BookOrderMapper bookOrderMapper) {
        this.orderService = orderService;
        this.userService = userService;
        this.bookService = bookService;
        this.orderMapper = orderMapper;
        this.bookOrderMapper = bookOrderMapper;
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getOrders() {
        List<OrderDTO> orderDTOs = orderService.getOrders().stream().map(
                order -> orderMapper.toDTO(
                        order,
                        userService.getUser(order.getUserId()),
                        getBookDTOs(order.getBooks())
                )
        ).collect(Collectors.toList());

        if (orderDTOs.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(orderDTOs);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody List<BookOrderDTO> bookOrderDTOs) {
        List<BookOrder> bookOrders = bookOrderDTOs.stream()
                .map(bookOrderMapper::toEntity).collect(Collectors.toList());
        Order order = orderService.createOrder(bookOrders);
        UserDTO user = userService.getUser(order.getUserId());
        List<BookDTO> bookDTOs = getBookDTOs(order.getBooks());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}").build(order.getId());
        return ResponseEntity.created(location)
                .body(orderMapper.toDTO(order, user, bookDTOs));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable int id) {
        Optional<Order> orderOptional = orderService.getOrderById(id);
        if (orderOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Order order = orderOptional.get();
        return ResponseEntity.ok(
                orderMapper.toDTO(
                        order,
                        userService.getUser(order.getUserId()),
                        getBookDTOs(order.getBooks())
                )
        );

    }

    private List<BookDTO> getBookDTOs(List<BookOrder> bookOrders) {
        return bookService.getBooksById(
                bookOrders.stream().map(BookOrder::getBookId).collect(Collectors.toList())
        );
    }

}
