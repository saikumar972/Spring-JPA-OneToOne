package com.onetoone.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDto {
    @NotNull(message = "name is required")
    private String name;
    @Size(min = 3,max = 20,message = "department is in between 3 and 20")
    private String department;
    @NotNull(message = "Address details required to proceed further")
    private AddressDto addressDto;
}
