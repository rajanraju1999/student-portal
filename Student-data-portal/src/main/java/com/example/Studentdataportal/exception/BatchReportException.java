package com.example.Studentdataportal.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BatchReportException extends RuntimeException{

    String string;
    public BatchReportException(String s) {

        string=s;
    }
}
