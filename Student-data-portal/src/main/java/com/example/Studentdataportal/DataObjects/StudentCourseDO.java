package com.example.Studentdataportal.DataObjects;

import com.example.Studentdataportal.Entitis.CourseEntity;
import com.example.Studentdataportal.Entitis.StudentEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL )
public class StudentCourseDO {



    private String studentid;


    private String courseid;


    private  String grade;


    // private CourseLogEntity logid;

    //private long totalattempts;

    private long semester;
    private String examdate;

}
