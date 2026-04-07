package com.example.proiectcontroller.book.service.query.impl;


import com.example.proiectcontroller.book.dtos.BookResponse;
import com.example.proiectcontroller.book.exceptions.BookNotFoundException;
import com.example.proiectcontroller.book.mappers.BookMapper;
import com.example.proiectcontroller.book.model.Book;
import com.example.proiectcontroller.book.repository.BookRepository;
import com.example.proiectcontroller.book.service.query.BookQueryService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class BookQueryServiceImpl implements BookQueryService {

    private BookRepository bookRepository;
    private BookMapper bookMapper;

    public BookQueryServiceImpl(BookRepository bookRepository, BookMapper bookMapper){
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BookResponse getBookById(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException());

        return bookMapper.toDto(book);

    }

    @Override
    public Optional<BookResponse> findByName(String bookName) {
        Book book = bookRepository.findByNameIgnoreCase(bookName)
                .orElseThrow(()->new BookNotFoundException());

        return Optional.ofNullable(bookMapper.toDto(book));
    }
}
