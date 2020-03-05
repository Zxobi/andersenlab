package com.andersenlab.bookstore.orderservice.service;

import com.andersenlab.bookstore.orderservice.model.dto.BookDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "book-service")
public interface BookService {

    @RequestMapping("/books")
    List<BookDTO> getBooksById(@RequestParam(value = "bookId", required = false) Iterable<Integer> ids);

}
