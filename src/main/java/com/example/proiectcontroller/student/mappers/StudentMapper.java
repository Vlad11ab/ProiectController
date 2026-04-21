package com.example.proiectcontroller.student.mappers;

import com.example.proiectcontroller.config.security.StudentPermissions;
import com.example.proiectcontroller.student.dtos.StudentCreateRequest;
import com.example.proiectcontroller.student.dtos.StudentCreateResponse;
import com.example.proiectcontroller.student.dtos.StudentResponse;
import com.example.proiectcontroller.student.model.Student;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

@Component
public class StudentMapper {

    public static Student toEntity(StudentCreateRequest req){
        if(req == null) return null;

        return Student.builder()
                .firstName(req.firstName())
                .lastName(req.lastName())
                .email(req.email())
                .build();
    }

    public static StudentResponse toDto(Student student){
        return new StudentResponse(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                copyPermissions(student.getPermissions())
        );
    }

    public static StudentCreateResponse studentToCreateResponse(Student student, String token){
        return new StudentCreateResponse(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                copyPermissions(student.getPermissions()),
                token
        );
    }

    private static Set<StudentPermissions> copyPermissions(Set<StudentPermissions> permissions) {
        if (permissions == null || permissions.isEmpty()) {
            return Collections.emptySet();
        }
        return EnumSet.copyOf(permissions);
    }

}
