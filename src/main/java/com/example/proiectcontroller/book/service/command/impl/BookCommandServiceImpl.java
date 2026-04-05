package com.example.proiectcontroller.book.service.command.impl;


import com.example.proiectcontroller.book.dtos.BookCreateRequest;
import com.example.proiectcontroller.book.dtos.BookPutRequest;
import com.example.proiectcontroller.book.dtos.BookResponse;
import com.example.proiectcontroller.book.dtos.BookPatchRequest;
import com.example.proiectcontroller.book.exceptions.BookAlreadyExistsException;
import com.example.proiectcontroller.book.exceptions.BookNotFoundException;
import com.example.proiectcontroller.book.mappers.BookMapper;
import com.example.proiectcontroller.book.model.Book;
import com.example.proiectcontroller.book.repository.BookRepository;
import com.example.proiectcontroller.book.service.command.BookCommandService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
public class BookCommandServiceImpl implements BookCommandService {

    private BookRepository bookRepository;
    private BookMapper bookMapper;

    public BookCommandServiceImpl(BookRepository bookRepository, BookMapper bookMapper){
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;


    }

    @Override
    @Transactional
    public BookResponse createBook(BookCreateRequest req) {
        if(bookRepository.findByName(req.name())){
            throw new BookAlreadyExistsException();
        }

        Book book = bookRepository.save(bookMapper.toEntity(req));

        return bookMapper.toDto(book);
    }

    @Override
    @Transactional
    public BookResponse patchBook(Long bookId, BookPatchRequest req) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException());

        if(req.name() != null){
            book.setName(req.name());
        }
        if(req.category() != null){
            book.setCategory(req.category());
        }

        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }

    @Override
    @Transactional
    public BookResponse updateBook(Long bookId, BookPutRequest req) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()-> new BookNotFoundException());

        book.setName(req.name());
        book.setCategory(req.category());

        Book updatedBook = bookRepository.save(book);
        return bookMapper.toDto(updatedBook);
    }

    @Override
    @Transactional
    public BookResponse deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException());

        bookRepository.delete(book);
        return bookMapper.toDto(book);
    }
}
