package com.andersenlab.bookstore.orderservice.common.mapper;

import com.andersenlab.bookstore.orderservice.common.dto.BookDTO;
import com.andersenlab.bookstore.orderservice.common.dto.BookOrderDTO;
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
                bookDTO,
                bookOrder.getCount()
        );
    }

}
