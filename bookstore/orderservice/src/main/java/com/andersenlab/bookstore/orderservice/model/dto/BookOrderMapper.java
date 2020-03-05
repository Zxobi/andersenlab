package com.andersenlab.bookstore.orderservice.model.dto;

import com.andersenlab.bookstore.orderservice.model.BookOrder;
import org.springframework.stereotype.Component;

@Component
public class BookOrderMapper {

    public BookOrder toEntity(BookOrderDTO bookOrderDTO) {
        return new BookOrder(
                bookOrderDTO.getBook().getId(),
                bookOrderDTO.getCount()
        );
    }

    public BookOrderDTO toDTO(BookOrder bookOrder, BookDTO bookDTO) {
        return new BookOrderDTO(
                bookOrder.getCount(),
                bookDTO
        );
    }

}
