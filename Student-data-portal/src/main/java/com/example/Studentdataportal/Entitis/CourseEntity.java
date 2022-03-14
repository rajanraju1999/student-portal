package com.example.Studentdataportal.Entitis;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseEntity {

    @Id
    @Column(name = "courseid")
    private String courseid;

    @Column(name = "courseName")
    private String courseName;

    @Column(name = "courseRegulation")
    private String courseRegulation;

    @Column(name = "courseType")
    private String courseType;

    @Column(name = "courseCredits")
    private String courseCredits;

}
