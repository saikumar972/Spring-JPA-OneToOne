package com.onetoone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StudentData {
    private String name;
    private String department;
    private String street;
    private String district;
    private String state;
}
