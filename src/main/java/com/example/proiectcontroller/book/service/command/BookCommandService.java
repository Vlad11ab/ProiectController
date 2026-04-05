package com.example.proiectcontroller.book.service.command;


import com.example.proiectcontroller.book.dtos.BookCreateRequest;
import com.example.proiectcontroller.book.dtos.BookPutRequest;
import com.example.proiectcontroller.book.dtos.BookResponse;
import com.example.proiectcontroller.book.dtos.BookPatchRequest;

public interface BookCommandService {
    BookResponse createBook(BookCreateRequest req);
    BookResponse patchBook(Long bookId, BookPatchRequest req);
    BookResponse updateBook(Long bookId, BookPutRequest req);
    BookResponse deleteBook(Long bookId);
}
