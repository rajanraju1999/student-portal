package com.example.Studentdataportal.exception;

import lombok.Data;

@Data
public class AlreadySemesterResultsExistForGivenBatchException extends RuntimeException{
    String string1;
    Long string2;

    public AlreadySemesterResultsExistForGivenBatchException(String batch, Long sem) {
        string1=batch;
        string2=sem;
    }
}
