package com.example.Studentdataportal.Controllers;

import com.example.Studentdataportal.DataObjects.StudentBacklogDO;
import com.example.Studentdataportal.DataObjects.StudentCourseDO;
import com.example.Studentdataportal.DataObjects.StudentDO;
import com.example.Studentdataportal.Repositorys.StudentRepository;
import com.example.Studentdataportal.Services.StudentCourseServices;
import com.example.Studentdataportal.Services.StudentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/filters")
public class FiltersController {
    @Autowired
    StudentCourseServices studentCourseServices;

    @GetMapping("/getstudentCourseGradeWiseList/{id}/{id1}")
    public ResponseEntity<List<StudentCourseDO>> getStudentCourseGradeWiseList(@PathVariable("id") String batch, @PathVariable("id1") String courseName)
    {
        List<StudentCourseDO> studentCourseDOList=  studentCourseServices.getStudentsByBatchAndCourse(batch,courseName);
        return new ResponseEntity<>(studentCourseDOList, HttpStatus.OK);
    }
    @GetMapping("/getstudentCourseFailedList/{id}/{id1}")
    public ResponseEntity<List<StudentCourseDO>> getstudentCourseFailedList(@PathVariable("id") String batch, @PathVariable("id1") String courseName)
    {
        List<StudentCourseDO> studentCourseDOList=  studentCourseServices.getFailedStudentsByBatchAndCourse(batch,courseName);
        return new ResponseEntity<>(studentCourseDOList, HttpStatus.OK);
    }
    @GetMapping("/getstudentsListWithatmostNBacklogs/{id}/{id1}")
    public ResponseEntity<List<StudentBacklogDO>> getstudentsListWithatmostNBacklogs(@PathVariable("id") String batch,@PathVariable("id1") int N)
    {
        List<StudentBacklogDO> studentBacklogDOList=  studentCourseServices.getstudentswithNmaxBacklogs(batch,N);
        return new ResponseEntity<>(studentBacklogDOList, HttpStatus.OK);
    }
    @GetMapping("/getstudentsListShortlistByCGPA/{id}/{id1}")
    public ResponseEntity<List<StudentDO>> getstudentsListShortlistByCGPA(@PathVariable("id") String batch,@PathVariable("id1") Float cgpa)
    {
        List<StudentDO> studentDO =  studentCourseServices.getstudentsListShortlistByCGPA(batch,cgpa);
        return new ResponseEntity<>(studentDO, HttpStatus.OK);
    }


}
