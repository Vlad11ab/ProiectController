package com.example.proiectcontroller.auth.dtos;

import com.example.proiectcontroller.config.security.StudentPermissions;

import java.util.Set;

public record AuthResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        Set<StudentPermissions> directPermissions,
        String token
){}
