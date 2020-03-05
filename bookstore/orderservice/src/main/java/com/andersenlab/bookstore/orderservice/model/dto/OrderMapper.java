package com.andersenlab.bookstore.orderservice.model.dto;

import com.andersenlab.bookstore.orderservice.model.BookOrder;
import com.andersenlab.bookstore.orderservice.model.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    private BookOrderMapper bookOrderMap;

    public OrderMapper(BookOrderMapper bookOrderMap) {
        this.bookOrderMap = bookOrderMap;
    }

    public Order toEntity(OrderDTO orderDTO) {
        return new Order(
                orderDTO.getId(),
                orderDTO.getTotalPrice(),
                orderDTO.getCreatedDate(),
                orderDTO.getUser().getId(),
                orderDTO.getBooks().stream()
                        .map(bookOrderDTO -> new BookOrder(bookOrderDTO.getBook().getId(), bookOrderDTO.getCount()))
                        .collect(Collectors.toList())
        );
    }

    public OrderDTO toDTO(Order order, UserDTO user, List<BookDTO> bookDTOs) {
        Map<Integer, BookDTO> bookIdToDTOMap = new HashMap<>();
        for (BookDTO bookDTO : bookDTOs) {
            bookIdToDTOMap.put(bookDTO.getId(), bookDTO);
        }

        List<BookOrderDTO> bookOrderDTOs = new ArrayList<>();
        for (BookOrder bookOrder : order.getBooks()) {
            bookOrderDTOs.add(
                    bookOrderMap.toDTO(bookOrder, bookIdToDTOMap.get(bookOrder.getBookId()))
            );
        }

        return new OrderDTO(
                order.getId(),
                order.getTotalPrice(),
                order.getCreatedDate(),
                user,
                bookOrderDTOs
        );
    }

}
