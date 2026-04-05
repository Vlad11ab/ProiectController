package com.example.proiectcontroller.book.exceptions;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException() {
        super("BOOK_NOT_FOUND_EXCEPTION");
    }
}
