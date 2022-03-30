package com.example.Studentdataportal.zattainmentmodule.DataObjects;

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
public class AttainmentReportDO {
    private String course;
    private String subject;
    private Float CO1;
    private Float CO2;
    private Float CO3;
    private Float CO4;
    private Float CO5;
    private Float averageCOattainment;
    private String attained;
    private String result;
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
