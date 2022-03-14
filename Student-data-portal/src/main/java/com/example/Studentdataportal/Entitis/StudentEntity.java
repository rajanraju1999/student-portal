package com.example.Studentdataportal.Entitis;

import java.util.*;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentEntity {


    @Id
    @Column(name = "rollnumber",nullable = false)
    private String rollnumber;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "emailid",nullable = false)
    //make email id unique afterwords
    private String emailid;

   // @Column(name = "year",nullable = false)
   // private String year;

    @Column(name = "section",nullable = false)
    private String section;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "batchid",referencedColumnName = "batch",nullable = false)
    private BatchEntity batchid;

    @Column(name = "yearofjoining",nullable = false)
    private String yearofjoining;

   // @OneToMany(mappedBy = "studentid")
    //@JoinColumn(name = "studentid")
   // private List< StudentCourseEntity> studentCourseEntityList = new ArrayList<>();
}
