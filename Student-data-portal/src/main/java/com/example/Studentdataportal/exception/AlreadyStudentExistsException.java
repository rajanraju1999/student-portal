package com.example.Studentdataportal.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlreadyStudentExistsException extends RuntimeException{

    String string;
}
