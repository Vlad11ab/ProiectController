package com.example.proiectcontroller.book.repository;

import ch.qos.logback.core.pattern.parser.OptionTokenizer;
import com.example.proiectcontroller.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select (count(b)>0) from Book b where lower(b.name) = :name")
    boolean existsByNameIgnoreCase(@Param("name") String name);

    @Query("select from Book b where lower(b.name) = :name")
    Optional<Book> findByName(@Param("name") String bookName);

    @Override
    List<Book> findAll();

    @Override
    Optional<Book> findById(Long bookId);

}
