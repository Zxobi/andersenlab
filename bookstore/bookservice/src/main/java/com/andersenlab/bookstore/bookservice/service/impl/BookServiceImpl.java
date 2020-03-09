package com.andersenlab.bookstore.bookservice.service.impl;

import com.andersenlab.bookstore.bookservice.common.dto.BookOrderDTO;
import com.andersenlab.bookstore.bookservice.common.mapper.BookMapper;
import com.andersenlab.bookstore.bookservice.model.Book;
import com.andersenlab.bookstore.bookservice.repository.BookRepository;
import com.andersenlab.bookstore.bookservice.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Transactional
    @Override
    public List<BookOrderDTO> reserveBooks(List<BookOrderDTO> bookOrders) {
        Map<Integer, BookOrderDTO> bookIdToBookOrderMap = new HashMap<>();
        bookOrders.forEach(bookOrder ->
            bookIdToBookOrderMap.compute(bookOrder.getBook().getId(), (id, currentBookOrder) -> {
                if (currentBookOrder == null) return bookOrder;

                currentBookOrder.setCount(currentBookOrder.getCount() + bookOrder.getCount());
                return currentBookOrder;
            })
        );

        getBooksById(bookIdToBookOrderMap.keySet()).forEach(book -> {
            BookOrderDTO bookOrder = bookIdToBookOrderMap.get(book.getId());
            if (bookOrder.getCount() > book.getQuantity()) {
                throw new RuntimeException("BookOrder count is higher than uantity for Book with id " + book.getId());
            }

            book.setQuantity(book.getQuantity() - bookOrder.getCount());
            bookOrder.setBook(bookMapper.toDTO(book));
        });

        return bookOrders;
    }

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getBooksById(Iterable<Integer> ids) {
        return bookRepository.findAllById(ids);
    }

    @Override
    public Optional<Book> getBook(int id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book updateBook(Book book) {
        return bookRepository.saveAndFlush(book);
    }

    @Override
    public Book createBook(Book book) {
        return bookRepository.saveAndFlush(book);
    }

    @Override
    public void deleteBookById(int id) {
        bookRepository.deleteById(id);
    }

}
