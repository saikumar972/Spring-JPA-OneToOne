package com.onetoone.service;

import com.onetoone.dao.StudentRepo;
import com.onetoone.dto.StudentDto;
import com.onetoone.dto.StudentRecord;
import com.onetoone.entity.Student;
import com.onetoone.exceptions.StudentExceptions;
import com.onetoone.utility.StudentDtoToEntityMapper;
import com.onetoone.utility.StudentResponseMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentServiceV3 {
    private final StudentRepo studentRepo;
    private final StudentResponseMapper studentResponseMapper;
    private final StudentDtoToEntityMapper studentDtoToEntityMapper;

    public StudentRecord addStudent(StudentDto studentInput){
        Student student=studentDtoToEntityMapper.apply(studentInput);
        studentRepo.save(student);
        return studentResponseMapper.apply(student);
    }
    public StudentRecord getStudentDetails(int id){
        Student student=studentRepo.findById(id).orElseThrow(()->new StudentExceptions("The entered id "+id+" is invalid id"));
        return studentResponseMapper.apply(student);
    }
}
