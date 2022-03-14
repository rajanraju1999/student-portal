package com.example.Studentdataportal.exception;

import lombok.Data;
import net.bytebuddy.implementation.bind.annotation.This;
@Data
public class CourseNameErrorInExcelException extends RuntimeException{
    String string;
    String string1;
    public CourseNameErrorInExcelException(String s,String ss) {

        string=s;
        string1=ss;
    }
}
