package com.example.Studentdataportal.exception;

import lombok.Data;

@Data
public class NoSuchCourseNameExistForGivenBatchException extends RuntimeException{
    String string1;
    String string2;

    public NoSuchCourseNameExistForGivenBatchException(String batch, String courseName) {
        string1=batch;
        string2=courseName;
    }
}
