package com.example.Studentdataportal.Util;

import com.example.Studentdataportal.DataObjects.StudentCourseDO;
import com.example.Studentdataportal.DataObjects.StudentDO;
import com.example.Studentdataportal.Entitis.CourseEntity;
import com.example.Studentdataportal.Entitis.StudentCourseEntity;
import com.example.Studentdataportal.Entitis.StudentEntity;
import com.example.Studentdataportal.Repositorys.CourseRepository;
import com.example.Studentdataportal.Repositorys.StudentCourseRepository;
import com.example.Studentdataportal.Repositorys.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentCourseConvert {

@Autowired
StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;
    public StudentCourseEntity convert2StudentCourseEntity(StudentCourseDO studentCourseDO)
    {
        return StudentCourseEntity.builder().studentid(studentRepository.getByRollnumber(studentCourseDO.getStudentid())).grade(studentCourseDO.getGrade()).courseid(courseRepository.getByCourseid(studentCourseDO.getCourseid()))
                .semester(studentCourseDO.getSemester()).examdate(studentCourseDO.getExamdate()).build();

    }

    public StudentCourseDO convert2StudentCourseDO(StudentCourseEntity studentCourseEntity)
    {
        StudentEntity studentEntity= studentCourseEntity.getStudentid();
        CourseEntity courseEntity= studentCourseEntity.getCourseid();
        return StudentCourseDO.builder().studentid(studentEntity.getRollnumber()).grade(studentCourseEntity.getGrade()).courseid(courseEntity.getCourseid()).coursename(courseEntity.getCourseName())
                .semester(studentCourseEntity.getSemester()).examdate(studentCourseEntity.getExamdate()).totalattempts(studentCourseEntity.getTotalattempts()).build();
    }
}
