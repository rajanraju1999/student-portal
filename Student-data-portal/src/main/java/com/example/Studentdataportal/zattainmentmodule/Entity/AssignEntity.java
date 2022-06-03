package com.example.Studentdataportal.zattainmentmodule.Entity;

import com.example.Studentdataportal.Entitis.BatchEntity;
import com.example.Studentdataportal.Entitis.CourseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "batchid",referencedColumnName = "batch",nullable = false)
    private BatchEntity batchid;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "courseid",referencedColumnName = "courseId")
    private CourseEntity courseid;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private String time;

    private String status;
}
