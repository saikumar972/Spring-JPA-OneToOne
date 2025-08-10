package com.onetoone.controller;

import com.onetoone.dto.StudentDto;
import com.onetoone.dto.StudentRecord;
import com.onetoone.entity.Student;
import com.onetoone.exceptions.StudentErrors;
import com.onetoone.service.StudentService;
import com.onetoone.service.StudentServiceV2;
import com.onetoone.service.StudentServiceV3;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
@AllArgsConstructor
@Tag(name = "student-update",description = "APIs for the insertion, update and deletion")
public class StudentController {
    private final StudentService studentService;
    private final StudentServiceV2 studentServiceV2;
    private final StudentServiceV3 studentServiceV3;

    @Operation(summary = "insert student",description = "This API is for inserting student data")
    @ApiResponse(responseCode = "201",description = "student inserted")
    @ApiResponse(responseCode = "400",description = "bad input", content = @Content(schema = @Schema(implementation = StudentErrors.class)))
    @ApiResponse(responseCode = "500",description = "Internal server error")
    @PostMapping("/add")
    public ResponseEntity<Student> addStudent(@RequestBody Student studentInput){
        Student student= studentService.addStudent(studentInput);
        return ResponseEntity.status(HttpStatus.CREATED).body(student);
    }

    @Operation(summary = "insert student record",description = "This API is for inserting student data")
    @ApiResponse(responseCode = "201",description = "student record inserted")
    @ApiResponse(responseCode = "400",description = "bad input", content = @Content(schema = @Schema(implementation = StudentErrors.class)))
    @ApiResponse(responseCode = "500",description = "Internal server error")
    @PostMapping("/add/v2")
    public ResponseEntity<StudentRecord>  addStudentV2(@RequestBody Student student){
        StudentRecord studentRecord= studentServiceV2.addStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentRecord);
    }

    @Operation(summary = "insert student record",description = "This API is for inserting student data")
    @ApiResponse(responseCode = "201",description = "student record inserted")
    @ApiResponse(responseCode = "400",description = "bad input", content = @Content(schema = @Schema(implementation = StudentErrors.class)))
    @ApiResponse(responseCode = "500",description = "Internal server error")
    @PostMapping("/add/v3")
    public ResponseEntity<StudentRecord> addStudentV3(@Valid @RequestBody StudentDto studentDto){
        StudentRecord studentRecord= studentServiceV3.addStudent(studentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentRecord);
    }

    //----------------------------------

    @Operation(summary = "Get Student by id",description = "This API is for getting the student details by id")
    @ApiResponse(responseCode = "200",description = "Fetched Student By Id successfully")
    @ApiResponse(responseCode = "404",description = "Invalid id entered", content = @Content(schema = @Schema(implementation = StudentErrors.class)))
    @ApiResponse(responseCode = "500",description = "Internal server error")
    @GetMapping("/id/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable int id){
        Student student= studentService.getStudentDetails(id);
        return ResponseEntity.status(HttpStatus.OK).body(student);
    }

    @Operation(summary = "Get Student record by id",description = "This API is for getting the student record by id")
    @ApiResponse(responseCode = "200",description = "Fetched Student record By Id successfully")
    @ApiResponse(responseCode = "404",description = "Invalid id entered", content = @Content(schema = @Schema(implementation = StudentErrors.class)))
    @ApiResponse(responseCode = "500",description = "Internal server error")
    @GetMapping("/id/v2/{id}")
    public ResponseEntity<StudentRecord> getStudentByIdV2(@PathVariable Integer id){
        StudentRecord studentRecord= studentServiceV2.getStudentDetails(id);
        return ResponseEntity.status(HttpStatus.OK).body(studentRecord);
    }

    @Operation(summary = "Get Student record by id",description = "This API is for getting the student record by id")
    @ApiResponse(responseCode = "200",description = "Fetched Student record By Id successfully")
    @ApiResponse(responseCode = "404",description = "Invalid id entered", content = @Content(schema = @Schema(implementation = StudentErrors.class)))
    @ApiResponse(responseCode = "500",description = "Internal server error")
    @GetMapping("/id/v3/{id}")
    public ResponseEntity<StudentRecord> getStudentByIdV3(@PathVariable int id){
        StudentRecord studentRecord= studentServiceV3.getStudentDetails(id);
        return ResponseEntity.status(HttpStatus.OK).body(studentRecord);
    }

}
