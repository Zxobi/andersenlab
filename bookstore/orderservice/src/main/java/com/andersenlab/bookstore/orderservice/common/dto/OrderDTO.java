package com.andersenlab.bookstore.orderservice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private int id;
    private float totalPrice;
    private Date createdDate;
    private UserDTO user;
    private String status;
    private List<BookOrderDTO> books;

}
