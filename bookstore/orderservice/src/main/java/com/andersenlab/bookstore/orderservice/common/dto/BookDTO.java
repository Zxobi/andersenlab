package com.andersenlab.bookstore.orderservice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private int id;
    private String title;
    private float price;
    private List<AuthorDTO> authors;

}
