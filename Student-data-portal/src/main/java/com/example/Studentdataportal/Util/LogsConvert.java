package com.example.Studentdataportal.Util;


import com.example.Studentdataportal.DataObjects.CourseDO;
import com.example.Studentdataportal.DataObjects.LogsDo;
import com.example.Studentdataportal.Entitis.CourseEntity;
import com.example.Studentdataportal.Entitis.StudentCourseLogEntity;
import org.springframework.stereotype.Component;

@Component
public class LogsConvert {

    public LogsDo convert2logsDO(StudentCourseLogEntity studentCourseLogEntity)
    {
       return LogsDo.builder().studentid(studentCourseLogEntity.getStudentid().getRollnumber()).courseid(studentCourseLogEntity.getCourseid().getCourseid()).
               attemptdate(studentCourseLogEntity.getAttemptdate()).attemptnumber(studentCourseLogEntity.getAttemptnumber()).grade(studentCourseLogEntity.getGrade()).build();
    }
}
