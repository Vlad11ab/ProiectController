package com.example.proiectcontroller.config;

import com.example.proiectcontroller.book.model.Book;
import com.example.proiectcontroller.book.repository.BookRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConfigApp {

    private BookRepository bookRepository;

    public ConfigApp(BookRepository bookRepository){
        this.bookRepository=bookRepository;

//        this.testFindAll();
    }

    public void testFindAll(){
        bookRepository.findAll().forEach(System.out::println);
    }

   @Bean
    Scanner createScanner(){
       return  new Scanner(System.in);
   }
}