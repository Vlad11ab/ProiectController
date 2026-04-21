package com.example.proiectcontroller.student.service.query;

import com.example.proiectcontroller.student.model.Student;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface StudentQueryService {
    Optional<Student> findByEmail(String email);

}
