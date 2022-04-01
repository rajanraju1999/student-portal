package com.example.Studentdataportal.exception;

import lombok.Data;

@Data
public class wrongColumnNameException extends RuntimeException{
String column_name;
String correct_column_name;
    public wrongColumnNameException(String s1,String s2) {
        column_name=s1;
        correct_column_name=s2;
    }
}
