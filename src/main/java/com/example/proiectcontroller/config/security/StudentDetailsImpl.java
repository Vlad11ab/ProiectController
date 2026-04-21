package com.example.proiectcontroller.config.security;

import com.example.proiectcontroller.student.repository.StudentRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class StudentDetailsImpl implements UserDetailsService {
    private StudentRepository studentRepository;

    public StudentDetailsImpl(StudentRepository studentRepository){
        this.studentRepository=studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return (UserDetails) studentRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User " + email + " not found"));
    }
}
