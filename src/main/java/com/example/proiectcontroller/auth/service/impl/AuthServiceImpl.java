package com.example.proiectcontroller.auth.service.impl;

import com.example.proiectcontroller.auth.dtos.AuthLoginRequest;
import com.example.proiectcontroller.auth.dtos.AuthResponse;
import com.example.proiectcontroller.auth.service.AuthService;
import com.example.proiectcontroller.config.jwt.JWTTokenProvider;
import com.example.proiectcontroller.config.security.StudentPermissions;
import com.example.proiectcontroller.student.dtos.StudentCreateRequest;
import com.example.proiectcontroller.student.dtos.StudentCreateResponse;
import com.example.proiectcontroller.student.exceptions.StudentAlreadyExistsException;
import com.example.proiectcontroller.student.mappers.StudentMapper;
import com.example.proiectcontroller.student.model.Student;
import com.example.proiectcontroller.student.repository.StudentRepository;
import com.example.proiectcontroller.student.service.command.StudentCommandService;
import com.example.proiectcontroller.student.service.query.StudentQueryService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    private StudentCommandService studentCommandService;
    private StudentQueryService studentQueryService;
    private AuthenticationManager authenticationManager;
    private JWTTokenProvider jwtTokenProvider;
    private StudentRepository studentRepository;
    private PasswordEncoder passwordEncoder;

    public AuthServiceImpl(StudentCommandService studentCommandService,
                           StudentQueryService studentQueryService,
                           AuthenticationManager authenticationManager,
                           JWTTokenProvider jwtTokenProvider,
                           StudentRepository studentRepository,
                           PasswordEncoder passwordEncoder) {
        this.studentCommandService = studentCommandService;
        this.studentQueryService = studentQueryService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public StudentCreateResponse register(StudentCreateRequest request) {
        if(studentRepository.existsByEmailJpql(request.email())){
            throw new StudentAlreadyExistsException();
        }

        Student student = StudentMapper.toEntity(request);
        student.setPassword(this.passwordEncoder.encode(request.password()));
        student.setPermissions(Set.of(StudentPermissions.STUDENT_READ,StudentPermissions.BOOK_READ, StudentPermissions.BOOK_DELETE));

        Student savedStudent = studentRepository.save(student);
        return StudentMapper.studentToCreateResponse(savedStudent, jwtTokenProvider.generateToken(savedStudent));
    }

    @Override
    public AuthResponse login(AuthLoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        Student student = studentQueryService.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException(request.email()));

        return new AuthResponse(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getPermissions(),
                jwtTokenProvider.generateToken(student)
        );
    }


}
