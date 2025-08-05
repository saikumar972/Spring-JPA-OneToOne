package com.onetoone.controller;

import com.onetoone.dto.StudentData;
import com.onetoone.dto.StudentDetails;
import com.onetoone.dto.StudentRecord;
import com.onetoone.service.StudentServiceV4;
import com.onetoone.service.StudentServiceV5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student/v2")
public class StudentControllerV2 {
    @Autowired
    StudentServiceV4 studentServiceV4;

    @Autowired
    StudentServiceV5 studentServiceV5;

    //pagination and sorting
    @GetMapping("/five/{name}")
    public ResponseEntity<List<StudentRecord>> getStudentByNameFiveRecords(@PathVariable String name){
        List<StudentRecord> studentRecords=studentServiceV4.getFiveStudentRecords(name);
        return ResponseEntity.status(HttpStatus.OK).body(studentRecords);
    }

    @GetMapping("/sort")
    public ResponseEntity<List<StudentRecord>> getStudentRecords(){
        List<StudentRecord> studentRecords=studentServiceV4.getStudentRecordsInSort();
        return ResponseEntity.status(HttpStatus.OK).body(studentRecords);
    }

    @GetMapping("/pageAndSort/{name}")
    public ResponseEntity<List<StudentRecord>> getStudentRecordsWithNameAndSort(@PathVariable String name){
        List<StudentRecord> studentRecords=studentServiceV4.getStudentRecordsWithNameAndSort(name);
        return ResponseEntity.status(HttpStatus.OK).body(studentRecords);
    }

    @GetMapping("/pages/{name}")
    public ResponseEntity<List<StudentRecord>> getStudentPages(@PathVariable String name){
        List<StudentRecord> studentRecords=studentServiceV4.getStudentDetailsInPages(name);
        return ResponseEntity.status(HttpStatus.OK).body(studentRecords);
    }

    //fetching the data from db only
    @GetMapping("/name/{name}")
    public ResponseEntity<StudentRecord> getStudentDetailsByName(@PathVariable String name){
        StudentRecord studentRecord=studentServiceV4.getStudentDetailsByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(studentRecord);
    }

    //fetch the basic student details
    @GetMapping("/names/v1/{name}")
    public ResponseEntity<List<StudentDetails>> getStudentBasicDetails(@PathVariable String name){
        List<StudentDetails> studentDetails=studentServiceV4.getStudentBasicDetails(name);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }

    @GetMapping("/names/v2/{name}")
    public ResponseEntity<List<StudentDetails>> getStudentDetailsStartsWith(@PathVariable String name){
        List<StudentDetails> studentDetails=studentServiceV4.getStudentDetailsStartsWith(name);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }

    @GetMapping("/names/v3/{name}")
    public ResponseEntity<List<StudentDetails>> getStudentBasicDetailsV3(@PathVariable String name){
        List<StudentDetails> studentDetails=studentServiceV4.getStudentBasicDetailsV3(name);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }

    //N+1 problem
    @GetMapping("/names/v4/{name}")
    public ResponseEntity<List<StudentRecord>> getStudentDetailsN(@PathVariable String name){
        List<StudentRecord> studentDetails=studentServiceV4.getStudentDetailsN(name);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }

    @GetMapping("/names/v6/{name}")
    public ResponseEntity<List<StudentRecord>> getStudentDetailsJoinFetch(@PathVariable String name){
        List<StudentRecord> studentDetails=studentServiceV4.getStudentDetailsJoinFetch(name);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }

    @GetMapping("/names/v7/{name}")
    public ResponseEntity<List<StudentRecord>> getStudentDetailsEntityGraph(@PathVariable String name){
        List<StudentRecord> studentDetails=studentServiceV4.getStudentDetailsEntityGraph(name);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }

    //update and delete
    @PutMapping("/update/{id}")
    public ResponseEntity<StudentRecord> updateStudentName(@PathVariable int id,@RequestParam String name){
        StudentRecord studentDetails=studentServiceV4.updateStudentName(id,name);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<StudentRecord> deleteStudentById(@PathVariable int id){
        StudentRecord studentDetails=studentServiceV4.deleteStudentById(id);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }

    //native query
    @GetMapping("/names/v8/{name}")
    public ResponseEntity<List<StudentDetails>> getStudentDetailsNative(@PathVariable String name){
        List<StudentDetails> studentDetails=studentServiceV4.getStudentDetailsNative(name);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }

    @GetMapping("/names/v9/{name}")
    public ResponseEntity<List<StudentDetails>> getStudentDetailsNativeV2(@PathVariable String name){
        List<StudentDetails> studentDetails=studentServiceV4.getStudentDetailsNativeV2(name);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }

    //dynamic native query
    @GetMapping("/names/v10/{name}")
    public ResponseEntity<List<StudentData>> dynamicNativeQuery(@PathVariable String name,@RequestParam(value = "state",required = false) String state){
        List<StudentData> studentDetails=studentServiceV5.dynamicNativeQuery(name,state);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }
    //criteria API
    @GetMapping("/names/v11/{name}")
    public ResponseEntity<List<StudentRecord>> dynamicQueryCriteriaApi(@PathVariable String name,@RequestParam(value = "state",required = false) String state){
        List<StudentRecord> studentDetails=studentServiceV5.getStudentDetailsByCriteriaBuilder(name,state);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }

    @GetMapping("/names/v12/{name}")
    public ResponseEntity<List<StudentRecord>> dynamicQueryCriteriaApiV2(@PathVariable String name,@RequestParam(value = "state",required = false) String state){
        List<StudentRecord> studentDetails=studentServiceV5.getStudentDetailsByCriteriaBuilderV2(name,state);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }

    @GetMapping("/names/v13/{name}")
    public ResponseEntity<List<StudentDetails>> dynamicQueryCriteriaApiSpecificFields(@PathVariable String name,@RequestParam(value = "state",required = false) String state){
        List<StudentDetails> studentDetails=studentServiceV5.getStudentBasicDetailsByCriteriaAPI(name,state);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }

    @GetMapping("/names/v14/{name}")
    public ResponseEntity<List<StudentDetails>> getStudentBasicDetailsByCriteriaAPIJoins(@PathVariable String name,@RequestParam(value = "state",required = false) String state){
        List<StudentDetails> studentDetails=studentServiceV5.getStudentBasicDetailsByCriteriaAPIJoins(name,state);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }

    //specification API

    @GetMapping("/names/v15/{name}")
    public ResponseEntity<List<StudentDetails>> getStudentDetailsUsingSpecificationAPI(@PathVariable String name,@RequestParam(value = "state",required = false) String state){
        List<StudentDetails> studentDetails=studentServiceV5.getStudentDetailsUsingSpecificationAPI(name,state);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }


    @GetMapping("/names/v16/{name}")
    public ResponseEntity<List<StudentRecord>> getStudentDetailsUsingSpecificationAPIV2(@PathVariable String name,@RequestParam(value = "state",required = false) String state){
        List<StudentRecord> studentDetails=studentServiceV5.getStudentDetailsUsingSpecificationAPIV2(name,state);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }





}
