package com.suchit.jwt.controller;

import com.suchit.jwt.entity.Student;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    List<Student> students = new ArrayList<>();

    {
        students.add(new Student(1, "Suchit", 100));
        students.add(new Student(2, "Rahul", 90));
        students.add(new Student(3, "Rohit", 80));
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        System.out.println("================================================================");
        System.out.println("This is a getStudents method of StudentController class");
        return students;
    }

    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student) {
        students.add(student);
        return student;
    }

    // get Csrf token
    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }
}

// NOTES:
//✅ New CSRF token is generated for every GET request
//✅ Same CSRF token is reused for multiple POST, PUT, DELETE, PATCH requests
//✅ When a new GET request is made, a new CSRF token is issued, invalidating the old one
