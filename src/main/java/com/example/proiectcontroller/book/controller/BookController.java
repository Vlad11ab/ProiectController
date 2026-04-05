package com.example.proiectcontroller.book.controller;

import com.example.proiectcontroller.book.dtos.BookCreateRequest;
import com.example.proiectcontroller.book.dtos.BookPatchRequest;
import com.example.proiectcontroller.book.dtos.BookPutRequest;
import com.example.proiectcontroller.book.dtos.BookResponse;
import com.example.proiectcontroller.book.service.command.BookCommandService;
import com.example.proiectcontroller.book.service.query.BookQueryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/books")
public class BookController {

    private BookCommandService bookCommandService;
    private BookQueryService bookQueryService;

    public BookController(BookCommandService bookCommandService, BookQueryService bookQueryService){
        this.bookCommandService = bookCommandService;
        this.bookQueryService = bookQueryService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookResponse>> getAllBooks(){
        log.info("HTTP GET ALL BOOKS METHOD");
        return ResponseEntity.status(HttpStatus.OK).body(bookQueryService.getAllBooks());
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long bookId){
        log.info("HTTP GET /api/v1/books/{}", bookId);
        return ResponseEntity.status(HttpStatus.OK).body(bookQueryService.getBookById(bookId));
    }

    @PostMapping("/add")
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookCreateRequest book){
        log.info("HTTP POST /api/v1/books name={} category={}", book.name(), book.category());
        return ResponseEntity.status(HttpStatus.CREATED).body(bookCommandService.createBook(book));
    }

    @PatchMapping("/edit/{bookId}")
    public ResponseEntity<BookResponse> patchBook(@PathVariable Long bookId, @Valid @RequestBody BookPatchRequest patched){
        log.info("HTTP PATCH /api/v1/books/{}", bookId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookCommandService.patchBook(bookId,patched));
    }

    @PutMapping("/update/{bookId}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long bookId, @Valid @RequestBody BookPutRequest updated){
        log.info("HTTP UPDATE /api/v1/books/edit/{}", bookId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookCommandService.updateBook(bookId,updated));
    }

    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId){
        log.info("HTTP DELETE /api/v1/books/{}", bookId);
        bookCommandService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }

}
