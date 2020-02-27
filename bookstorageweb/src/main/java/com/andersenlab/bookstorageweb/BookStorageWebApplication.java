package com.andersenlab.bookstorageweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class BookStorageWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookStorageWebApplication.class, args);
    }

}
