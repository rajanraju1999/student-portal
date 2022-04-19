package com.example.Studentdataportal.exception;

import lombok.Data;

@Data
public class StudentFromOtherBatchExistsException extends RuntimeException{

    public StudentFromOtherBatchExistsException() {

    }
}
