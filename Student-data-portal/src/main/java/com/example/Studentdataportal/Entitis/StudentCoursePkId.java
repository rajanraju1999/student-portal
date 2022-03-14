package com.example.Studentdataportal.Entitis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudentCoursePkId implements Serializable {

    private StudentEntity studentid;
    private CourseEntity courseid;


    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof StudentCoursePkId))
            return false;
        StudentCoursePkId castOther = (StudentCoursePkId) other;

        return studentid.getRollnumber().equals(castOther.getStudentid().getRollnumber()) && courseid.getCourseid().equals(castOther.getCourseid().getCourseid());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(studentid.getRollnumber(),courseid.getCourseid());
    }
}
