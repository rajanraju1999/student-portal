package com.example.Studentdataportal.DataObjects;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeneralReportDO {



    private String coursename;
    private Integer noofstudentsappeared;
    private Integer noofstudentspassed;
    private Integer passpercentage;
    private Integer Ogreadscount;
    private Integer Aplusgreadscount;
    private Integer Agreadscount;
    private Integer noofstudentsappearedinallsub;
    private Integer noofstudentspassedinallsub;
    private Integer overalpasspercentage;




}
