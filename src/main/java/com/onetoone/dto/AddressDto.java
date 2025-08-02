package com.onetoone.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressDto {
    @NotNull(message = "street is required")
    private String street;
    @NotNull(message = "please mention the district name")
    private String district;
    @NotNull(message = "Please mention the state name")
    private String state;
}
