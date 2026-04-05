package com.example.proiectcontroller.book.dtos;

import jakarta.validation.constraints.NotBlank;

public record BookCreateRequest(

        @NotBlank
        String name,

        @NotBlank
        String category


){}
