package com.example.Studentdataportal.zattainmentmodule.Entity;

import com.example.Studentdataportal.Entitis.BatchEntity;
import com.example.Studentdataportal.Entitis.CourseEntity;
import com.example.Studentdataportal.Entitis.StudentEntity;
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
public class AttainmentEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "batchid",referencedColumnName = "batch",nullable = false)
        private BatchEntity batchid;


        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "courseid",referencedColumnName = "courseId")
        private CourseEntity courseid;


        @Column(name = "COtype")
        private String COtype;

        @Column(name = "COvalue")
        private Float COvalue;

        @Column(name = "PO1")
        private Float PO1;

        @Column(name = "PO2")
        private Float PO2;

        @Column(name = "PO3")
        private Float PO3;

        @Column(name = "PO4")
        private Float PO4;

        @Column(name = "PO5")
        private Float PO5;

        @Column(name = "PO6")
        private Float PO6;

        @Column(name = "PO7")
        private Float PO7;

        @Column(name = "PO8")
        private Float PO8;

        @Column(name = "PO9")
        private Float PO9;

        @Column(name = "PO10")
        private Float PO10;

        @Column(name = "PO11")
        private Float PO11;

        @Column(name = "PO12")
        private Float PO12;

        @Column(name = "PSO1")
        private Float PSO1;

        @Column(name = "PSO2")
        private Float PSO2;
}
