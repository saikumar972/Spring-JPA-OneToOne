package com.onetoone.service;

import com.onetoone.dao.StudentRepo;
import com.onetoone.dto.StudentDto;
import com.onetoone.dto.StudentRecord;
import com.onetoone.entity.Student;
import com.onetoone.exceptions.StudentExceptions;
import com.onetoone.utility.StudentDtoToEntityMapper;
import com.onetoone.utility.StudentResponseMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentServiceV4 {
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

    //pagination
    public List<StudentRecord> getFiveStudentRecords(String name){
        Pageable page= PageRequest.of(0,5);
        List<Student> students=studentRepo.studentRecordsStartWith(name,page);
        List<StudentRecord> studentRecords=students.stream()
                .map(studentResponseMapper)
                .toList();
        return studentRecords;
    }

    //sorting
    public List<StudentRecord> getStudentRecordsInSort(){
        Sort sort=Sort.by(
                Sort.Order.asc("name"),
                Sort.Order.desc("id")
        );
        List<Student> students= studentRepo.findAll(sort);
        return students.stream().map(studentResponseMapper).toList();
    }

    //pagination and sorting
    public List<StudentRecord> getStudentRecordsWithNameAndSort(String name){
        Sort sort=Sort.by(
                Sort.Order.asc("name"),
                Sort.Order.asc("id")
        );
        Pageable page=PageRequest.of(0,5,sort);
        List<Student> students=studentRepo.findStudentWithNameAndSort(name,page);
        return students.stream().map(studentResponseMapper).toList();
    }

    //fetch only to see the content
    public List<StudentRecord> getStudentsAndSort(String name){
        Sort sort=Sort.by("name","id").descending();
        Pageable page=PageRequest.of(0,5,sort);
        Page<Student> studentRecordPage=studentRepo.findStudentsWithNameAndSort(name,page);
        List<Student> students=studentRecordPage.getContent();
        System.out.println("checking is the firstPage or not :"+studentRecordPage.isFirst());
        System.out.println("checking is the lastPage or not :"+studentRecordPage.isLast());
        return students.stream().map(studentResponseMapper).toList();
    }

}
