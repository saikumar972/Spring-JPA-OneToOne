package com.onetoone.utility;

import com.onetoone.dto.StudentRecord;
import com.onetoone.entity.Student;
import org.springframework.stereotype.Component;

import java.util.function.Function;
@Component
public class StudentResponseMapper implements Function<Student, StudentRecord> {
    @Override
    public StudentRecord apply(Student student) {
        return new StudentRecord(
                student.getId(),
                student.getName(),
                student.getDepartment(),
                student.getAddress()
        );
    }
}
