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


    private double sgpa1;

    private double sgpa2;

    private double sgpa3;

    private double sgpa4;

    private double sgpa5;

    private double sgpa6;

    private double sgpa7;

    private double sgpa8;

    private double cgpa;

}
