package com.example.Studentdataportal.Util;


import com.example.Studentdataportal.DataObjects.LogsDO;
import com.example.Studentdataportal.Entitis.StudentCourseLogEntity;
import org.springframework.stereotype.Component;

@Component
public class LogsConvert {

    public LogsDO convert2logsDO(StudentCourseLogEntity studentCourseLogEntity)
    {
       return LogsDO.builder().studentid(studentCourseLogEntity.getStudentid().getRollnumber()).courseid(studentCourseLogEntity.getCourseid().getCourseid()).coursename(studentCourseLogEntity.getCourseid().getCourseName()).
               attemptdate(studentCourseLogEntity.getAttemptdate()).attemptnumber(studentCourseLogEntity.getAttemptnumber()).grade(studentCourseLogEntity.getGrade()).build();
    }
}
