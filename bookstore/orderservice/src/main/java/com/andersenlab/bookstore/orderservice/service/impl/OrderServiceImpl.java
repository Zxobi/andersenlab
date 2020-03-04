package com.andersenlab.bookstore.orderservice.service.impl;

import com.andersenlab.bookstore.orderservice.model.Book;
import com.andersenlab.bookstore.orderservice.model.Order;
import com.andersenlab.bookstore.orderservice.model.OrderDTO;
import com.andersenlab.bookstore.orderservice.model.User;
import com.andersenlab.bookstore.orderservice.repository.OrderRepository;
import com.andersenlab.bookstore.orderservice.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static final int TIMEOUT_BLOCK_DURATION_SECONDS = 30;

    private final OrderRepository orderRepository;

    private final WebClient bookServiceWebClient;
    private final WebClient userServiceWebClient;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.bookServiceWebClient = WebClient.create("http://localhost:9101");
        this.userServiceWebClient = WebClient.create("http://localhost:9103");
    }

    @Override
    public List<OrderDTO> getOrders() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            return Collections.emptyList();
        }

        HashMap<Integer, List<OrderDTO>> userIdToOrderDTO = new HashMap<>();
        HashMap<Integer, List<OrderDTO>> bookIdToOrderDTO = new HashMap<>();
        List<OrderDTO> orderDTOs = new ArrayList<>();
        for (Order order : orders) {
            OrderDTO orderDTO = orderToOrderDTO(order, new ArrayList<>(), null);
            orderDTOs.add(orderDTO);

            userIdToOrderDTO.computeIfAbsent(order.getUserId(), ArrayList::new).add(orderDTO);
            for (Integer bookId : order.getBookIds()) {
                bookIdToOrderDTO.computeIfAbsent(bookId, ArrayList::new).add(orderDTO);
            }
        }

        Mono<User[]> usersMono = getUsersMono(userIdToOrderDTO.keySet());
        Mono<Book[]> booksMono = getBooksMono(bookIdToOrderDTO.keySet());

        User[] users = usersMono.block(Duration.ofSeconds(TIMEOUT_BLOCK_DURATION_SECONDS));
        Book[] books = booksMono.block(Duration.ofSeconds(TIMEOUT_BLOCK_DURATION_SECONDS));

        for (User user : users) {
            userIdToOrderDTO.get(user.getId())
                    .forEach(orderDTO -> orderDTO.setUser(user));
        }
        for (Book book : books) {
            bookIdToOrderDTO.get(book.getId())
                    .forEach(orderDTO -> orderDTO.getBooks().add(book));
        }

        return orderDTOs;
    }

    @Override
    public Optional<OrderDTO> getOrderById(int id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        return orderOptional.flatMap(this::withBooksAndUser);
    }

    @Override
    public OrderDTO createOrder(int userId, List<Book> books) {
        float totalPrice = books.stream().map(Book::getPrice).reduce(0f, Float::sum);
        List<Integer> bookIds = books.stream().map(Book::getId).collect(Collectors.toList());

        Order order = new Order(0, totalPrice, new Date(), userId, bookIds);

        order = orderRepository.saveAndFlush(order);
        return orderToOrderDTO(order, books, new User(userId, ""));
    }

    private Optional<OrderDTO> withBooksAndUser(Order order) {
        Mono<Book[]> booksMono = getBooksMono(order.getBookIds());
        Mono<User> userMono = getUserMono(order.getUserId());

        return booksMono.zipWith(userMono, (books, user) ->
                orderToOrderDTO(order, Arrays.asList(books), user))
                .blockOptional(Duration.ofSeconds(TIMEOUT_BLOCK_DURATION_SECONDS));
    }

    private Mono<User> getUserMono(int userId) {
        return userServiceWebClient.get().uri("/users/" + userId)
                .retrieve().bodyToMono(User.class);
    }

    private Mono<User[]> getUsersMono(Collection<Integer> userIds) {
        return userServiceWebClient.get().uri(uriBuilder -> {
            uriBuilder.path("/users");
            for (Integer id : userIds) {
                uriBuilder.queryParam("userId", id);
            }
            return uriBuilder.build();
        }).retrieve().bodyToMono(User[].class);
    }

    private Mono<Book[]> getBooksMono(Collection<Integer> bookIds) {
        return bookServiceWebClient.get().uri(uriBuilder -> {
            uriBuilder.path("/books");
            for (Integer id : bookIds) {
                uriBuilder.queryParam("bookId", id);
            }
            return uriBuilder.build();
        }).retrieve().bodyToMono(Book[].class);
    }

    private OrderDTO orderToOrderDTO(Order order, List<Book> books, User user) {
        return new OrderDTO(
                order.getId(),
                order.getTotalPrice(),
                order.getCreatedDate(),
                user,
                books
        );
    }

}
