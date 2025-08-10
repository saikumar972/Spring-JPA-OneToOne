package com.onetoone.controller;

import com.onetoone.dto.StudentData;
import com.onetoone.dto.StudentDetails;
import com.onetoone.dto.StudentRecord;
import com.onetoone.entity.Student;
import com.onetoone.exceptions.StudentErrors;
import com.onetoone.service.StudentServiceV4;
import com.onetoone.service.StudentServiceV5;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student/v2")
@Tag(name = "Student controller v2", description = "This controller is meant for fetching the student data in multiple sources")
public class StudentControllerV2 {
    @Autowired
    StudentServiceV4 studentServiceV4;

    @Autowired
    StudentServiceV5 studentServiceV5;

    //pagination and sorting
    @Operation(summary = "fetch 5 names by name", description = "fetching the first 5 records by name")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200",description = "name starts with input were successfully fetched"),
                    @ApiResponse(responseCode = "404",description = "invalid name given",content = @Content(schema = @Schema(implementation = StudentErrors.class)))
            }
    )
    @GetMapping("/five/{name}")
    public ResponseEntity<List<StudentRecord>> getStudentByNameFiveRecords(@PathVariable String name){
        List<StudentRecord> studentRecords=studentServiceV4.getFiveStudentRecords(name);
        return ResponseEntity.status(HttpStatus.OK).body(studentRecords);
    }

    @Operation(summary = "sorting",description = "sort the records based on name")
    @ApiResponse(responseCode = "200",description = "sort the records based on name successfully")
    @ApiResponse(responseCode = "404",description = "Name not found",content = @Content(schema = @Schema(implementation = StudentErrors.class)))
    @GetMapping("/sort")
    public ResponseEntity<List<StudentRecord>> getStudentRecords(){
        List<StudentRecord> studentRecords=studentServiceV4.getStudentRecordsInSort();
        return ResponseEntity.status(HttpStatus.OK).body(studentRecords);
    }

    @Operation(summary = "pagination and sorting",description = "pagination and sort the records based on name")
    @ApiResponse(responseCode = "200",description = "paginated and sort the records based on name successfully")
    @ApiResponse(responseCode = "404",description = "Name not found",content = @Content(schema = @Schema(implementation = StudentErrors.class)))
    @GetMapping("/pageAndSort/{name}")
    public ResponseEntity<List<StudentRecord>> getStudentRecordsWithNameAndSort(@PathVariable String name){
        List<StudentRecord> studentRecords=studentServiceV4.getStudentRecordsWithNameAndSort(name);
        return ResponseEntity.status(HttpStatus.OK).body(studentRecords);
    }

    @Operation(summary = "pages info",description = "pagination the records based on name")
    @ApiResponse(responseCode = "200",description = "paginated the records based on name successfully")
    @ApiResponse(responseCode = "404",description = "Name not found",content = @Content(schema = @Schema(implementation = StudentErrors.class)))
    @GetMapping("/pages/{name}")
    public ResponseEntity<List<StudentRecord>> getStudentPages(@PathVariable String name){
        List<StudentRecord> studentRecords=studentServiceV4.getStudentDetailsInPages(name);
        return ResponseEntity.status(HttpStatus.OK).body(studentRecords);
    }

    @Operation(summary = "pages info",description = "pagination the records based on name")
    @ApiResponse(responseCode = "200",description = "paginated the records based on name successfully")
    @ApiResponse(responseCode = "404",description = "Name not found",content = @Content(schema = @Schema(implementation = StudentErrors.class)))
    @GetMapping("/pages/v2/{name}")
    public ResponseEntity<Page<Student>> getStudentsPageable(@PathVariable String name,@ParameterObject Pageable pageable){
        Page<Student> studentRecords= studentServiceV4.getStudentDetailsInPagesV2(name,pageable);
        return ResponseEntity.status(HttpStatus.OK).body(studentRecords);
    }

    //fetching the data from db only
    @Operation(summary = "Fetch student details by exact name", description = "Fetches complete details of a student entity for the given exact name.")
    @ApiResponse(responseCode = "200", description = "Student details fetched successfully")
    @ApiResponse(responseCode = "404", description = "Student with given name not found", content = @Content(schema = @Schema(implementation = StudentErrors.class)))
    @GetMapping("/name/{name}")
    public ResponseEntity<StudentRecord> getStudentDetailsByName(@Parameter(description = "give valid name") @PathVariable String name){
        StudentRecord studentRecord=studentServiceV4.getStudentDetailsByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(studentRecord);
    }

    //fetch the basic student details
    @Operation(summary = "Fetch basic student details", description = "Fetches only basic details (name, department, state) of students with an exact name match.")
    @ApiResponse(responseCode = "200", description = "Basic student details fetched successfully")
    @ApiResponse(responseCode = "404", description = "No students found for given name", content = @Content(schema = @Schema(implementation = StudentErrors.class)))
    @GetMapping("/names/v1/{name}")
    public ResponseEntity<List<StudentDetails>> getStudentBasicDetails(@PathVariable String name){
        List<StudentDetails> studentDetails=studentServiceV4.getStudentBasicDetails(name);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }

    @Operation(summary = "Fetch students with names starting with given prefix", description = "Fetches basic student details for names starting with the given prefix.")
    @ApiResponse(responseCode = "200", description = "Student details fetched successfully")
    @ApiResponse(responseCode = "404", description = "No matching students found", content = @Content(schema = @Schema(implementation = StudentErrors.class)))
    @GetMapping("/names/v2/{name}")
    public ResponseEntity<List<StudentDetails>> getStudentDetailsStartsWith(@PathVariable String name){
        List<StudentDetails> studentDetails=studentServiceV4.getStudentDetailsStartsWith(name);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }

    @Operation(summary = "Fetch basic details - Version 3", description = "Fetches student details using another repository method for demonstration purposes.")
    @ApiResponse(responseCode = "200", description = "Student details fetched successfully")
    @ApiResponse(responseCode = "404", description = "No matching students found", content = @Content(schema = @Schema(implementation = StudentErrors.class)))
    @GetMapping("/names/v3/{name}")
    public ResponseEntity<List<StudentDetails>> getStudentBasicDetailsV3(@PathVariable String name){
        List<StudentDetails> studentDetails=studentServiceV4.getStudentBasicDetailsV3(name);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }

    //N+1 problem
    @Operation(summary = "Demonstrate N+1 problem in fetching", description = "Fetches student details by name using a default fetch strategy that may trigger N+1 queries.")
    @ApiResponse(responseCode = "200", description = "Student details fetched successfully (N+1 generated)")
    @ApiResponse(responseCode = "404", description = "No matching students found", content = @Content(schema = @Schema(implementation = StudentErrors.class)))
    @GetMapping("/names/v4/{name}")
    public ResponseEntity<List<StudentRecord>> getStudentDetailsN(@PathVariable String name){
        List<StudentRecord> studentDetails=studentServiceV4.getStudentDetailsN(name);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }

    @Operation(summary = "Fetch student details using JOIN FETCH", description = "Uses JPQL JOIN FETCH to solve the N+1 problem by fetching Address with Student in one query.")
    @ApiResponse(responseCode = "200", description = "Student details fetched successfully with JOIN FETCH")
    @ApiResponse(responseCode = "404", description = "No matching students found", content = @Content(schema = @Schema(implementation = StudentErrors.class)))
    @GetMapping("/names/v6/{name}")
    public ResponseEntity<List<StudentRecord>> getStudentDetailsJoinFetch(@PathVariable String name){
        List<StudentRecord> studentDetails=studentServiceV4.getStudentDetailsJoinFetch(name);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }

    @Operation(summary = "Fetch student details using EntityGraph", description = "Uses an EntityGraph to solve the N+1 problem by eagerly loading related entities.")
    @ApiResponse(responseCode = "200", description = "Student details fetched successfully with EntityGraph")
    @ApiResponse(responseCode = "404", description = "No matching students found", content = @Content(schema = @Schema(implementation = StudentErrors.class)))
    @GetMapping("/names/v7/{name}")
    public ResponseEntity<List<StudentRecord>> getStudentDetailsEntityGraph(@PathVariable String name){
        List<StudentRecord> studentDetails=studentServiceV4.getStudentDetailsEntityGraph(name);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }

    //update and delete
    @Operation(summary = "Update student name by ID", description = "Updates only the name field of a student identified by the given ID.")
    @ApiResponse(responseCode = "200", description = "Student name updated successfully")
    @ApiResponse(responseCode = "404", description = "No student found for given ID", content = @Content(schema = @Schema(implementation = StudentErrors.class)))
    @PutMapping("/update/{id}")
    public ResponseEntity<StudentRecord> updateStudentName(@PathVariable int id,@RequestParam String name){
        StudentRecord studentDetails=studentServiceV4.updateStudentName(id,name);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }

    @Operation(summary = "Delete student by ID", description = "Deletes the student record identified by the given ID from the database.")
    @ApiResponse(responseCode = "200", description = "Student deleted successfully")
    @ApiResponse(responseCode = "404", description = "No student found for given ID", content = @Content(schema = @Schema(implementation = StudentErrors.class)))
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<StudentRecord> deleteStudentById(@PathVariable int id){
        StudentRecord studentDetails=studentServiceV4.deleteStudentById(id);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }

    //native query
    @Operation(summary = "Fetch student details using native SQL", description = "Executes a native SQL query to retrieve student basic details for a given name.")
    @ApiResponse(responseCode = "200", description = "Student details fetched via native query")
    @ApiResponse(responseCode = "404", description = "No students found", content = @Content(schema = @Schema(implementation = StudentErrors.class)))
    @GetMapping("/names/v8/{name}")
    public ResponseEntity<List<StudentDetails>> getStudentDetailsNative(@PathVariable String name){
        List<StudentDetails> studentDetails=studentServiceV4.getStudentDetailsNative(name);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }

    @Operation(summary = "Fetch student details using native SQL V2", description = "Executes another version of the native SQL query to fetch student details.")
    @ApiResponse(responseCode = "200", description = "Student details fetched via native query V2")
    @ApiResponse(responseCode = "404", description = "No students found", content = @Content(schema = @Schema(implementation = StudentErrors.class)))
    @GetMapping("/names/v9/{name}")
    public ResponseEntity<List<StudentDetails>> getStudentDetailsNativeV2(@PathVariable String name){
        List<StudentDetails> studentDetails=studentServiceV4.getStudentDetailsNativeV2(name);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }

    //dynamic native query
    @Operation(summary = "Dynamic native query", description = "Builds a native query dynamically based on name and optional state, returns StudentData.")
    @ApiResponse(responseCode = "200", description = "Dynamic native query executed successfully")
    @ApiResponse(responseCode = "404", description = "No students matching criteria", content = @Content(schema = @Schema(implementation = StudentErrors.class)))
    @GetMapping("/names/v10/{name}")
    public ResponseEntity<List<StudentData>> dynamicNativeQuery(@PathVariable String name,@RequestParam(value = "state",required = false) String state){
        List<StudentData> studentDetails=studentServiceV5.dynamicNativeQuery(name,state);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }

    //criteria API
    @Operation(summary = "Dynamic Criteria API query (StudentRecord)", description = "Builds a criteria query dynamically with optional state filter.")
    @ApiResponse(responseCode = "200", description = "Criteria query executed successfully")
    @ApiResponse(responseCode = "404", description = "No students matching criteria", content = @Content(schema = @Schema(implementation = StudentErrors.class)))
    @GetMapping("/names/v11/{name}")
    public ResponseEntity<List<StudentRecord>> dynamicQueryCriteriaApi(@PathVariable String name,@RequestParam(value = "state",required = false) String state){
        List<StudentRecord> studentDetails=studentServiceV5.getStudentDetailsByCriteriaBuilder(name,state);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }

    @Operation(summary = "Dynamic Criteria API query V2 (StudentRecord)", description = "Improved version for combining predicates dynamically.")
    @ApiResponse(responseCode = "200", description = "Criteria query executed successfully (V2)")
    @ApiResponse(responseCode = "404", description = "No students matching criteria", content = @Content(schema = @Schema(implementation = StudentErrors.class)))
    @GetMapping("/names/v12/{name}")
    public ResponseEntity<List<StudentRecord>> dynamicQueryCriteriaApiV2(@PathVariable String name,@RequestParam(value = "state",required = false) String state){
        List<StudentRecord> studentDetails=studentServiceV5.getStudentDetailsByCriteriaBuilderV2(name,state);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }

    @Operation(summary = "Criteria API for specific fields", description = "Fetches only selected student fields with optional state filter.")
    @ApiResponse(responseCode = "200", description = "Partial fields fetched successfully")
    @ApiResponse(responseCode = "404", description = "No students matching criteria", content = @Content(schema = @Schema(implementation = StudentErrors.class)))
    @GetMapping("/names/v13/{name}")
    public ResponseEntity<List<StudentDetails>> dynamicQueryCriteriaApiSpecificFields(@PathVariable String name,@RequestParam(value = "state",required = false) String state){
        List<StudentDetails> studentDetails=studentServiceV5.getStudentBasicDetailsByCriteriaAPI(name,state);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }

    @Operation(summary = "Criteria API with joins for basic details", description = "Uses Criteria API joins to fetch specific fields with optional state filter.")
    @ApiResponse(responseCode = "200", description = "Partial fields via join fetched successfully")
    @ApiResponse(responseCode = "404", description = "No students matching criteria", content = @Content(schema = @Schema(implementation = StudentErrors.class)))
    @GetMapping("/names/v14/{name}")
    public ResponseEntity<List<StudentDetails>> getStudentBasicDetailsByCriteriaAPIJoins(@PathVariable String name,@RequestParam(value = "state",required = false) String state){
        List<StudentDetails> studentDetails=studentServiceV5.getStudentBasicDetailsByCriteriaAPIJoins(name,state);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }

    //specification API
    @Operation(summary = "Specification API for basic details", description = "Filters student records using Specifications, fetching specific fields.")
    @ApiResponse(responseCode = "200", description = "Specification query executed successfully")
    @ApiResponse(responseCode = "404", description = "No students matching criteria", content = @Content(schema = @Schema(implementation = StudentErrors.class)))
    @GetMapping("/names/v15/{name}")
    public ResponseEntity<List<StudentDetails>> getStudentDetailsUsingSpecificationAPI(@PathVariable String name,@RequestParam(value = "state",required = false) String state){
        List<StudentDetails> studentDetails=studentServiceV5.getStudentDetailsUsingSpecificationAPI(name,state);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }

    @Operation(summary = "Specification API V2 for StudentRecord", description = "Filters student records using Specifications, fetching full StudentRecord.")
    @ApiResponse(responseCode = "200", description = "Specification query executed successfully (V2)")
    @ApiResponse(responseCode = "404", description = "No students matching criteria", content = @Content(schema = @Schema(implementation = StudentErrors.class)))
    @GetMapping("/names/v16/{name}")
    public ResponseEntity<List<StudentRecord>> getStudentDetailsUsingSpecificationAPIV2(@PathVariable String name,@RequestParam(value = "state",required = false) String state){
        List<StudentRecord> studentDetails=studentServiceV5.getStudentDetailsUsingSpecificationAPIV2(name,state);
        return ResponseEntity.status(HttpStatus.OK).body(studentDetails);
    }

}
