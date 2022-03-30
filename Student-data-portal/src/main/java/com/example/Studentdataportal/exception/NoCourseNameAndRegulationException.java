package com.example.Studentdataportal.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
public class NoCourseNameAndRegulationException extends RuntimeException{
    String string;
    String string1;

    public NoCourseNameAndRegulationException(String courseName, String regulation) {
    }
}
