package com.example.Studentdataportal.exception;

import lombok.Data;

@Data
public class AlreadyCourseNameExistForGivenBatchException extends RuntimeException{
    String string1;
    String string2;

    public AlreadyCourseNameExistForGivenBatchException(String batch, String courseName) {
    }
}
