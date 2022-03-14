package com.example.Studentdataportal.exception;

import lombok.Data;

@Data
public class StudentNotExistsInDBException extends RuntimeException{
    String string;
    public StudentNotExistsInDBException(String s) {

        string=s;
    }
}
