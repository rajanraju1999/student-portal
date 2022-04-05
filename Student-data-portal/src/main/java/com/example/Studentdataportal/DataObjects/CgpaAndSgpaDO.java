package com.example.Studentdataportal.DataObjects;

import com.example.Studentdataportal.Entitis.StudentEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL )
public class CgpaAndSgpaDO {


    private StudentEntity studentid;


    private String sgpa1;

    private String sgpa2;

    private String sgpa3;

    private String sgpa4;

    private String sgpa5;

    private String sgpa6;

    private String sgpa7;

    private String sgpa8;

    private String cgpa;

}
