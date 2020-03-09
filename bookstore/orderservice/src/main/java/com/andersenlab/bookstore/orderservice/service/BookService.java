package com.andersenlab.bookstore.orderservice.service;

import com.andersenlab.bookstore.orderservice.common.dto.BookDTO;
import com.andersenlab.bookstore.orderservice.common.dto.BookOrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "book-service")
public interface BookService {

    @GetMapping("/books")
    List<BookDTO> getBooksById(@RequestParam(value = "bookId", required = false) Iterable<Integer> ids);

    @RequestMapping("/books/reserve")
    List<BookOrderDTO> reserveBooks(@RequestBody List<BookOrderDTO> bookOrders);

}
