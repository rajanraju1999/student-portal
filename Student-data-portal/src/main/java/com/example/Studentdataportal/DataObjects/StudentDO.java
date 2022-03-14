package com.example.Studentdataportal.DataObjects;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL )
public class StudentDO {


    private String id;
    private String name;
    private String emailid;
    private String section;
    private String batchid;
    private String yearofjoining;


}
