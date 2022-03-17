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
public class CgpaAndSgpaEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rollnumber",referencedColumnName = "rollnumber")
    private StudentEntity studentid;


    @Column(name = "sgpa1")
    private String sgpa1;
    @Column(name = "sgpa2")
    private String sgpa2;
    @Column(name = "sgpa3")
    private String sgpa3;
    @Column(name = "sgpa4")
    private String sgpa4;
    @Column(name = "sgpa5")
    private String sgpa5;
    @Column(name = "sgpa6")
    private String sgpa6;
    @Column(name = "sgpa7")
    private String sgpa7;
    @Column(name = "sgpa8")
    private String sgpa8;
    @Column(name = "cgpa")
    private String cgpa;



}
