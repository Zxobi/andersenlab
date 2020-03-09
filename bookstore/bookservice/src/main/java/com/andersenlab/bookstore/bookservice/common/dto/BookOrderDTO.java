package com.andersenlab.bookstore.bookservice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookOrderDTO {

    private BookDTO book;
    private int count;

}
