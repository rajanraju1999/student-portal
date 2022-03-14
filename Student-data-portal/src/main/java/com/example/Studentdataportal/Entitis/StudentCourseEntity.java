package com.example.Studentdataportal.Entitis;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentCourseEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "rollnumber",referencedColumnName = "rollnumber")
    private StudentEntity studentid;


    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "courseId",referencedColumnName = "courseId")
    private CourseEntity courseid;

    @Column(name = "grade")
    private  String grade;

    // @Column(name = "logid",nullable = false)
    // private CourseLogEntity logid;
    @Column(name = "totalattempts")
    private long totalattempts;

    @Column(name = "semester")
    private long semester;

    @Column(name = "examdate")
    private String examdate;


}
