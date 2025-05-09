package com.onetoone.controller;

import com.onetoone.entity.Student;
import com.onetoone.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @PostMapping("/add")
    public Student addStudent(@RequestBody Student student){
        return studentService.addStudent(student);
    }

    @GetMapping("/id/{id}")
    public Student getStudentById(@PathVariable int id){
        return studentService.getStudentDetails(id);
    }
}
