package com.onetoone.service;

import com.onetoone.dao.StudentRepo;
import com.onetoone.entity.Student;
import com.onetoone.exceptions.StudentExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    StudentRepo studentRepo;

    public Student addStudent(Student student){
        return studentRepo.save(student);
    }
    public Student getStudentDetails(int id){
        return studentRepo.findById(id).orElseThrow(()->new StudentExceptions("The entered id "+id+" is invalid id"));
    }
}
