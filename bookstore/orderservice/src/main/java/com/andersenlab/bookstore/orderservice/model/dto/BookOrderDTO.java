package com.andersenlab.bookstore.orderservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookOrderDTO {

    private int count;
    private BookDTO book;

}
