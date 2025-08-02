package com.onetoone.dto;

import com.onetoone.entity.Address;


public record StudentRecord (
         int studentId,
         String StudentName,
         String StudentDepartment,
         Address studentAddress
){
}
