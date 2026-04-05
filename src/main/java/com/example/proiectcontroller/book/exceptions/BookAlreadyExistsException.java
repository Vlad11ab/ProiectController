package com.example.proiectcontroller.book.exceptions;

public class BookAlreadyExistsException extends RuntimeException {
    public BookAlreadyExistsException() {
        super("BOOK_ALREADY_EXISTS_EXCEPTION");
    }
}
