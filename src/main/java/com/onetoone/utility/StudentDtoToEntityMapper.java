package com.onetoone.utility;

import com.onetoone.dto.StudentDto;
import com.onetoone.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;
@Component
public class StudentDtoToEntityMapper implements Function<StudentDto , Student> {
    @Autowired
    AddressDtoToEntity addressDtoToEntity;
    @Override
    public Student apply(StudentDto studentDto) {
        return Student.builder()
                .name(studentDto.getName())
                .department(studentDto.getDepartment())
                .address(addressDtoToEntity.apply(studentDto.getAddressDto()))
                .build();
    }
}
