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
public class StudentCourseLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "rollnumber",referencedColumnName = "rollnumber")
    private StudentEntity studentid;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "courseId",referencedColumnName = "courseId")
    private CourseEntity courseid;


    @Column(name = "attemptdate")
    private String attemptdate;

    @Column(name = "grade")
    private String grade;

    @Column(name = "attemptnumber")
    private Long attemptnumber;

}
