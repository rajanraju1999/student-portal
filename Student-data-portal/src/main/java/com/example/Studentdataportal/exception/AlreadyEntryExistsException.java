package com.example.Studentdataportal.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlreadyEntryExistsException extends RuntimeException{

    String string;
    String string1;
}
