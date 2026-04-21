package com.example.proiectcontroller.auth.controller;

import com.example.proiectcontroller.auth.dtos.AuthLoginRequest;
import com.example.proiectcontroller.auth.dtos.AuthResponse;
import com.example.proiectcontroller.auth.service.AuthService;
import com.example.proiectcontroller.student.dtos.StudentCreateRequest;
import com.example.proiectcontroller.student.dtos.StudentCreateResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
@Tag(name = "Authentication", description = "Public endpoints for account registration and JWT login")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService=authService;
    }

    @PostMapping("/register")
    public ResponseEntity<StudentCreateResponse> register(@Valid @RequestBody StudentCreateRequest request) {
        log.info("HTTP POST /api/v1/auth/register");
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthLoginRequest request) {
        log.info("HTTP POST /api/v1/auth/login");
        return ResponseEntity.ok(authService.login(request));
    }
}
