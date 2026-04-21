package com.example.proiectcontroller.student.dtos;

import com.example.proiectcontroller.config.security.StudentPermissions;

import java.util.Set;

public record StudentCreateResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        Set<StudentPermissions> directPermissions,
        String token
){}
