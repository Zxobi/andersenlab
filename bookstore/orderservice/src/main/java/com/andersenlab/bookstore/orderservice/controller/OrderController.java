package com.andersenlab.bookstore.orderservice.controller;

import com.andersenlab.bookstore.orderservice.model.OrderDTO;
import com.andersenlab.bookstore.orderservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        orderService.createOrder(orderDTO.getUser().getId(), orderDTO.getBooks());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}").build(orderDTO.getId());
        return ResponseEntity.created(location).body(orderDTO);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<OrderDTO> getBookById(@PathVariable int id) {
        return ResponseEntity.of(orderService.getOrderById(id));
    }

}
