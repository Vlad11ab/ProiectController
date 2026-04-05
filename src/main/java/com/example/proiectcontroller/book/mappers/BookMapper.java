package com.example.proiectcontroller.book.mappers;


import com.example.proiectcontroller.book.dtos.BookCreateRequest;
import com.example.proiectcontroller.book.dtos.BookResponse;
import com.example.proiectcontroller.book.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public Book toEntity(BookCreateRequest req){
        if (req == null) return null;

        return Book.builder()
                .name(req.name())
                .category(req.category())
                .build();
    }

    public BookResponse toDto(Book book){

        return new BookResponse(
                book.getId(),
                book.getName(),
                book.getCategory()
                );
    }
}
