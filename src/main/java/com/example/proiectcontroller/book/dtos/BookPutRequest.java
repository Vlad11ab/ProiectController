package com.example.proiectcontroller.book.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookPutRequest(
        @NotBlank
        @NotNull
        String name,
        @NotBlank
        @NotNull
        String category

){}
