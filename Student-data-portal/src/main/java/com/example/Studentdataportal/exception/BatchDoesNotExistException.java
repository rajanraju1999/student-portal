package com.example.Studentdataportal.exception;

        import lombok.Data;
        import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BatchDoesNotExistException extends RuntimeException{

    String string;
    public BatchDoesNotExistException(String s) {

        string=s;
    }
}
