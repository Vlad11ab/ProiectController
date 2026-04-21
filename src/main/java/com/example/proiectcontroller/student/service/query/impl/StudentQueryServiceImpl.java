package com.example.proiectcontroller.student.service.query.impl;

import com.example.proiectcontroller.student.model.Student;
import com.example.proiectcontroller.student.repository.StudentRepository;
import com.example.proiectcontroller.student.service.query.StudentQueryService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StudentQueryServiceImpl implements StudentQueryService {
    private final StudentRepository studentRepository;

    public StudentQueryServiceImpl(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    @Override
    public Optional<Student> findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

}
