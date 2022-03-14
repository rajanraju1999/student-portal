package com.example.Studentdataportal.DataObjects;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL )
public class CourseDO {
    private String courseId;

    private String courseName;

    private String courseRegulation;

    private String courseType;

    private String courseCredits;
}
