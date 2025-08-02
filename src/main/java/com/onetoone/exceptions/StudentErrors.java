package com.onetoone.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentErrors {
    private String errorMessage;
    private HttpStatus httpStatus;
    private int statusCode;
}
