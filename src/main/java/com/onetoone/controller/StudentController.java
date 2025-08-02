package com.onetoone.controller;

import com.onetoone.dto.StudentDto;
import com.onetoone.dto.StudentRecord;
import com.onetoone.entity.Student;
import com.onetoone.service.StudentService;
import com.onetoone.service.StudentServiceV2;
import com.onetoone.service.StudentServiceV3;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
@AllArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final StudentServiceV2 studentServiceV2;
    private final StudentServiceV3 studentServiceV3;
    @PostMapping("/add")
    public ResponseEntity<Student> addStudent(@RequestBody Student studentInput){
        Student student= studentService.addStudent(studentInput);
        return ResponseEntity.status(HttpStatus.CREATED).body(student);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable int id){
        Student student= studentService.getStudentDetails(id);
        return ResponseEntity.status(HttpStatus.OK).body(student);
    }

    @PostMapping("/add/v2")
    public ResponseEntity<StudentRecord>  addStudentV2(@RequestBody Student student){
        StudentRecord studentRecord= studentServiceV2.addStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentRecord);
    }

    @GetMapping("/id/v2/{id}")
    public ResponseEntity<StudentRecord> getStudentByIdV2(@PathVariable Integer id){
        StudentRecord studentRecord= studentServiceV2.getStudentDetails(id);
        return ResponseEntity.status(HttpStatus.OK).body(studentRecord);
    }

    @PostMapping("/add/v3")
    public ResponseEntity<StudentRecord> addStudentV3(@Valid @RequestBody StudentDto studentDto){
        StudentRecord studentRecord= studentServiceV3.addStudent(studentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentRecord);
    }

    @GetMapping("/id/v3/{id}")
    public ResponseEntity<StudentRecord> getStudentByIdV3(@PathVariable int id){
        StudentRecord studentRecord= studentServiceV3.getStudentDetails(id);
        return ResponseEntity.status(HttpStatus.OK).body(studentRecord);
    }
}
