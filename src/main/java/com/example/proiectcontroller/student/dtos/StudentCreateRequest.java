package com.example.proiectcontroller.student.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StudentCreateRequest(
        @NotBlank
        @Size(min = 3, max = 50, message = "size 3-50")
        String firstName,

        @NotBlank
        @Size(min = 3, max = 50, message = "size 3-50")
        String lastName,

        @Email
        @NotBlank
        @Size(min = 3, max = 50, message = "size 3-50")
        String email,

        @NotBlank
        @Size(min = 3, max = 50, message = "size 3-50")
        String password

){}
