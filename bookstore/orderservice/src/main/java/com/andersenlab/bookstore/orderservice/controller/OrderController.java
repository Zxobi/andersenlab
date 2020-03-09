package com.andersenlab.bookstore.orderservice.controller;

import com.andersenlab.bookstore.orderservice.common.Authorities;
import com.andersenlab.bookstore.orderservice.common.dto.BookOrderDTO;
import com.andersenlab.bookstore.orderservice.common.dto.JWTPayloadDTO;
import com.andersenlab.bookstore.orderservice.common.dto.OrderDTO;
import com.andersenlab.bookstore.orderservice.service.AuthService;
import com.andersenlab.bookstore.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final AuthService authService;

    public OrderController(OrderService orderService, AuthService authService) {
        this.orderService = orderService;
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getOrders(@RequestHeader("JWT-token") String authToken) {
        JWTPayloadDTO jwtPayload;
        if ((jwtPayload = validateAuthToken(authToken)) == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(orderService.getOrdersForUser(jwtPayload.getUser()));
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestHeader("JWT-token") String authToken, @RequestBody List<BookOrderDTO> bookOrderDTOs) {
        JWTPayloadDTO jwtPayload;
        if ((jwtPayload = validateAuthToken(authToken)) == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        OrderDTO order = orderService.createOrder(bookOrderDTOs, jwtPayload.getUser());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}").build(order.getId());
        return ResponseEntity.created(location).body(order);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<OrderDTO> getOrderById(@RequestHeader("JWT-token") String authToken, @PathVariable int id) {
        JWTPayloadDTO jwtPayload;
        if ((jwtPayload = validateAuthToken(authToken)) == null
                || !jwtPayload.getAuthorities().contains(Authorities.ADMIN.name())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.of(orderService.getOrderById(id));
    }

    private JWTPayloadDTO validateAuthToken(String authToken) {
        if (authToken == null || authToken.isEmpty()) {
            return null;
        }

        return authService.validate(authToken);
    }

}
