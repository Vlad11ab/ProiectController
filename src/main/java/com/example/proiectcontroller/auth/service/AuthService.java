package com.example.proiectcontroller.auth.service;

import com.example.proiectcontroller.auth.dtos.AuthLoginRequest;
import com.example.proiectcontroller.auth.dtos.AuthResponse;
import com.example.proiectcontroller.student.dtos.StudentCreateRequest;
import com.example.proiectcontroller.student.dtos.StudentCreateResponse;

public interface AuthService {
    StudentCreateResponse register(StudentCreateRequest request);
    AuthResponse login(AuthLoginRequest request);
}
