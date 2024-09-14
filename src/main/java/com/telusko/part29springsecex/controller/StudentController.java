package com.telusko.part29springsecex.controller;

import com.telusko.part29springsecex.model.Student;
import com.telusko.part29springsecex.service.JWTService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {
    @Autowired
    private JWTService jwtService;

    private List<Student> students = new ArrayList<>(
            List.of(
                    new Student(1, "Navin", 60),
                    new Student(2, "Kiran", 65)
            ));


    @GetMapping("/students")
    public List<Student> getStudents(@RequestHeader("Authorization") String authorizationHeader) {
        // Extract token from "Bearer <token>" format
        String token = authorizationHeader.substring(7);

        // Extract username from the token
        String username = jwtService.extractUserName(token);

        // Log or use the username as needed
        System.out.println("Request made by user: " + username);

        // Return students list
        return students;
    }

    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");


    }


    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student) {
        students.add(student);
        return student;
    }

}
