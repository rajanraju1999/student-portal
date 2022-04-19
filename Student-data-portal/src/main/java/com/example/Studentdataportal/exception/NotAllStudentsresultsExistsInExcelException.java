package com.example.Studentdataportal.exception;

import lombok.Data;

@Data
public class NotAllStudentsresultsExistsInExcelException extends RuntimeException{
    public NotAllStudentsresultsExistsInExcelException() {
    }
}
