package com.example.Studentdataportal.zattainmentmodule.DataObjects;


import com.example.Studentdataportal.Entitis.BatchEntity;
import com.example.Studentdataportal.Entitis.CourseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL )
public class AttainmentDO {
    private String batchid;
    private String courseid;
    private String COtype;
    private Float COvalue;
    private Float PO1;
    private Float PO2;
    private Float PO3;
    private Float PO4;
    private Float PO5;
    private Float PO6;
    private Float PO7;
    private Float PO8;
    private Float PO9;
    private Float PO10;
    private Float PO11;
    private Float PO12;
    private Float PSO1;
    private Float PSO2;
}
