package com.onetoone.service;

import com.onetoone.dao.StudentRepo;
import com.onetoone.dto.StudentRecord;
import com.onetoone.entity.Student;
import com.onetoone.exceptions.StudentExceptions;
import com.onetoone.utility.StudentResponseMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentServiceV2 {
   private final StudentRepo studentRepo;
    private final StudentResponseMapper studentResponseMapper;

    public StudentRecord addStudent(Student studentInput){
        Student student=studentRepo.save(studentInput);
       StudentRecord studentRecord= studentResponseMapper.apply(student);
        return studentRecord;
    }
    public StudentRecord getStudentDetails(Integer id){
        Student student= studentRepo.findById(id).orElseThrow(()->new StudentExceptions("The entered id "+id+" is invalid id"));
        return studentResponseMapper.apply(student);
    }
}
