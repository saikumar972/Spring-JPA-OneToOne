package com.onetoone.service;

import com.onetoone.dao.StudentRepo;
import com.onetoone.dto.StudentDetails;
import com.onetoone.dto.StudentRecord;
import com.onetoone.entity.Student;
import com.onetoone.utility.StudentDtoToEntityMapper;
import com.onetoone.utility.StudentResponseMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class StudentServiceV4 {
    private final StudentRepo studentRepo;
    private final StudentResponseMapper studentResponseMapper;
    private final StudentDtoToEntityMapper studentDtoToEntityMapper;

    //pagination
    public List<StudentRecord> getFiveStudentRecords(String name){
        Pageable page = PageRequest.of(0, 5);
        List<Student> students = studentRepo.studentRecordsStartWith(name, page);
        return students.stream()
                .map(studentResponseMapper)
                .toList();
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

    //fetch pages to see the content
    public List<StudentRecord> getStudentDetailsInPages(String name){
        Sort sort=Sort.by("name","id").descending();
        Pageable page=PageRequest.of(0,5,sort);
        Page<Student> studentRecordPage=studentRepo.findStudentsWithNameAndSort(name,page);
        List<Student> students=studentRecordPage.getContent();
        System.out.println("checking is the firstPage or not :"+studentRecordPage.isFirst());
        System.out.println("checking is the lastPage or not :"+studentRecordPage.isLast());
        System.out.println("Total pages were "+studentRecordPage.getTotalPages());
        return students.stream().map(studentResponseMapper).toList();
    }

    public Page<Student> getStudentDetailsInPagesV2(String name, Pageable pageable) {
        return studentRepo.getStudentsPagesByName(name, pageable);
    }


    //queries
    //fetch the student details
    public StudentRecord getStudentDetailsByName(String name){
        Student student=studentRepo.getStudentDetailsByName(name);
        return studentResponseMapper.apply(student);
    }

    //fetch the basic student details
    public List<StudentDetails> getStudentBasicDetails(String name){
        List<Object[]> basicDetails=studentRepo.getBasicStudentDetails(name);
        List<StudentDetails> studentDetails=new ArrayList<>();
        for(Object[] object:basicDetails){
            String studentName=(String)object[0];
            String studentDepartment=(String) object[1];
            String state=(String) object[2];
            studentDetails.add(new StudentDetails(studentName,studentDepartment,state));
        }
        return studentDetails;
    }

    public List<StudentDetails> getStudentBasicDetailsV3(String name){
        return studentRepo.getBasicDetailsV2(name);
    }

    public List<StudentDetails> getStudentDetailsStartsWith(String name){
        return studentRepo.getStudentBasicDetailsNameStartsWith(name);
    }

    //N+1 problem
    public List<StudentRecord> getStudentDetailsN(String name) {
        List<Student> students=studentRepo.getStudentDetailsN(name);
        return students.stream()
                .map(studentResponseMapper)
                .toList();
    }


    public List<StudentRecord> getStudentDetailsJoinFetch(String name) {
        List<Student> students=studentRepo.getStudentDetailsJoinFetch(name);
        return students.stream()
                .map(studentResponseMapper)
                .toList();
    }

    public List<StudentRecord> getStudentDetailsEntityGraph(String name) {
        List<Student> students=studentRepo.findStudentByName(name);
        return students.stream()
                .map(studentResponseMapper)
                .toList();
    }

    //update and delete
    public StudentRecord updateStudentName(int id,String name){
        int rowsImpacted=studentRepo.updateStudentById(id,name);
        log.info("StudentServiceV4 :: updateStudentName Method invoked and impacted rows {}",rowsImpacted);
        Student student=studentRepo.findById(id).orElseThrow(()->new IllegalArgumentException("Invalid id "+id));
        StudentRecord studentRecord= studentResponseMapper.apply(student);
        System.out.println(studentRecord.studentId());
        return studentRecord;
    }

    public StudentRecord deleteStudentById(int id){
        studentRepo.deleteStudentById(id);
        Student student=studentRepo.findById(id).orElseThrow(()->new IllegalArgumentException("Invalid id"));
        return studentResponseMapper.apply(student);
    }

    //native query
    public List<StudentDetails> getStudentDetailsNative(String name) {
        List<Object[]> details = studentRepo.studentDetailsNative(name);
        return details.stream()
                .map(detail -> new StudentDetails((String) detail[0], (String) detail[1], (String) detail[2]))
                .toList();
    }

    public List<StudentDetails> getStudentDetailsNativeV2(String name){
        return studentRepo.studentDetailsNativeV2(name);
    }
    
}
