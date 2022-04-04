package com.example.Studentdataportal.DataObjects;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL )
public class StudentBacklogDO {
    private String studentid;
    private List<StudentCourseDO> studentCourseDOList;
}
