package com.onetoone.exceptions;

public class StudentExceptions extends RuntimeException{
    String message;
    public StudentExceptions(String message){
        super(message);
    }
}
