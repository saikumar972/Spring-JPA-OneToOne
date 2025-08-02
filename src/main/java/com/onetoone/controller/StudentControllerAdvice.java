package com.onetoone.controller;

import com.onetoone.exceptions.StudentErrors;
import com.onetoone.exceptions.StudentExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class StudentControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> methodArgumentsNotValid(MethodArgumentNotValidException exception){
        Map<String,String> errorMap=new HashMap<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error->
                        errorMap.put(error.getField(),error.getDefaultMessage())
                );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
    }

    @ExceptionHandler(StudentExceptions.class)
    public ResponseEntity<StudentErrors>  illegalArgumentMethod(StudentExceptions exception){
        StudentErrors studentErrors= StudentErrors.builder()
                .errorMessage(exception.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(studentErrors);
    }
}
