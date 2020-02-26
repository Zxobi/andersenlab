package com.andersenlab.bookstorageweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class BookStorageWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookStorageWebApplication.class, args);
    }

}
