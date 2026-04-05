package com.example.proiectcontroller.book.service.query;


import com.example.proiectcontroller.book.dtos.BookResponse;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface BookQueryService {

    List<BookResponse> getAllBooks();
    BookResponse getBookById(Long bookId);
    Optional<BookResponse> findByName(String bookName);

}
