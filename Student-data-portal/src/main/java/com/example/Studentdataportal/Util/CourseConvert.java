package com.example.Studentdataportal.Util;


import com.example.Studentdataportal.DataObjects.CourseDO;
import com.example.Studentdataportal.DataObjects.StudentDO;
import com.example.Studentdataportal.Entitis.CourseEntity;
import com.example.Studentdataportal.Entitis.StudentEntity;
import org.springframework.stereotype.Component;

@Component
public class CourseConvert {
    public CourseEntity convert2CourseEntity(CourseDO courseDO)
    {
        return CourseEntity.builder().courseid(courseDO.getCourseId()).courseName(courseDO.getCourseName())
                .courseRegulation(courseDO.getCourseRegulation()).courseType(courseDO.getCourseType()).courseCredits(courseDO.getCourseCredits()).build();

    }

    public CourseDO convert2CourseDO(CourseEntity courseEntity)
    {
       return CourseDO.builder().courseId(courseEntity.getCourseid()).courseName(courseEntity.getCourseName())
               .courseType(courseEntity.getCourseType()).courseRegulation(courseEntity.getCourseRegulation()).courseCredits(courseEntity.getCourseCredits()).build();
    }
}
