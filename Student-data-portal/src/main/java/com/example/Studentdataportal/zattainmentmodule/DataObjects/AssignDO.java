package com.example.Studentdataportal.zattainmentmodule.DataObjects;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL )
public class AssignDO {
    private String batch;
    private String courseId;
    private Date date;
    private String time;
    private String status;
}
