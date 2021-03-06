package com.andersenlab.bookstore.bookservice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {

    private int id;
    private String fullName;

}
