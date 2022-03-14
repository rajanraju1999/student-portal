package com.example.Studentdataportal.Util;

import com.example.Studentdataportal.DataObjects.StudentCourseDO;
import com.example.Studentdataportal.DataObjects.StudentDO;
import com.example.Studentdataportal.Entitis.CourseEntity;
import com.example.Studentdataportal.Entitis.StudentCourseEntity;
import com.example.Studentdataportal.Entitis.StudentEntity;
import org.springframework.stereotype.Component;

@Component
public class StudentCourseConvert {


   /* public StudentCourseEntity convert2StudentCourseEntity(StudentCourseDO studentCourseDO)
    {
        return StudentCourseEntity.builder().studentid(studentCourseDO.getStudentid()).grade(studentCourseDO.getGrade()).courseid(studentCourseDO.getCourseid())
                .semester(studentCourseDO.getSemester()).build();

    }*/

    public StudentCourseDO convert2StudentCourseDO(StudentCourseEntity studentCourseEntity)
    {
        StudentEntity studentEntity= studentCourseEntity.getStudentid();
        CourseEntity courseEntity= studentCourseEntity.getCourseid();
        return StudentCourseDO.builder().studentid(studentEntity.getRollnumber()).grade(studentCourseEntity.getGrade()).courseid(courseEntity.getCourseid())
                .semester(studentCourseEntity.getSemester()).examdate(studentCourseEntity.getExamdate()).build();
    }
}
