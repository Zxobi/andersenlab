package com.andersenlab.bookstore.orderservice.controller;

import com.andersenlab.bookstore.orderservice.model.BookOrder;
import com.andersenlab.bookstore.orderservice.model.Order;
import com.andersenlab.bookstore.orderservice.model.dto.*;
import com.andersenlab.bookstore.orderservice.service.OrderService;
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
    private final OrderMapper orderMapper;
    private final BookOrderMapper bookOrderMapper;

    public OrderController(OrderService orderService, OrderMapper orderMapper, BookOrderMapper bookOrderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
        this.bookOrderMapper = bookOrderMapper;
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getOrders() {
        List<OrderDTO> orderDTOs = orderService.getOrders().stream().map(
                order -> orderMapper.toDTO(order, orderService.getOrderUser(order), orderService.getOrderBooks(order))
        ).collect(Collectors.toList());

        if (orderDTOs.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(orderDTOs);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody List<BookOrderDTO> bookOrderDTOs) {
        List<BookOrder> bookOrders = bookOrderDTOs.stream()
                .map(bookOrderMapper::toEntity).collect(Collectors.toList());
        Order order = orderService.createOrder(bookOrders);
        UserDTO user = orderService.getOrderUser(order);
        List<BookDTO> bookDTOs = orderService.getOrderBooks(order);
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
        return ResponseEntity.ok(orderMapper.toDTO(order, orderService.getOrderUser(order),
                orderService.getOrderBooks(order)));

    }

}
