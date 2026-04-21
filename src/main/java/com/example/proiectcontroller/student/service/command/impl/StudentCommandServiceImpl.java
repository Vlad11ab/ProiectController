package com.example.proiectcontroller.student.service.command.impl;

import com.example.proiectcontroller.config.jwt.JWTTokenProvider;
import com.example.proiectcontroller.student.dtos.StudentCreateRequest;
import com.example.proiectcontroller.student.dtos.StudentCreateResponse;
import com.example.proiectcontroller.student.exceptions.StudentAlreadyExistsException;
import com.example.proiectcontroller.student.mappers.StudentMapper;
import com.example.proiectcontroller.student.model.Student;
import com.example.proiectcontroller.student.repository.StudentRepository;
import com.example.proiectcontroller.student.service.command.StudentCommandService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class StudentCommandServiceImpl implements StudentCommandService {
    private StudentRepository studentRepository;
    private JWTTokenProvider jwtTokenProvider;
    private PasswordEncoder passwordEncoder;
    private StudentMapper studentMapper;

    public StudentCommandServiceImpl(StudentRepository studentRepository,
                                     JWTTokenProvider jwtTokenProvider,
                                     PasswordEncoder passwordEncoder,
                                     StudentMapper studentMapper){
        this.studentRepository = studentRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.studentMapper = studentMapper;
    }

//    @Override
//    @Transactional
//    public StudentCreateResponse create(StudentCreateRequest req) {
//        if(studentRepository.existsByEmailJpql(req.email())){
//            throw new StudentAlreadyExistsException();
//        }
//
//
//        Student student = studentMapper.toEntity(req);
//
//        Student savedStudent = studentRepository.save(student);
//        return studentMapper.studentToCreateResponse(savedStudent, jwtTokenProvider.generateToken(savedStudent));
//    }
}
