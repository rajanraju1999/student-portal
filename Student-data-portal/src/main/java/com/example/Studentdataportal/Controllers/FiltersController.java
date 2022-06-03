package com.example.Studentdataportal.Controllers;

import com.example.Studentdataportal.DataObjects.CgpaAndSgpaDO;
import com.example.Studentdataportal.DataObjects.StudentBacklogDO;
import com.example.Studentdataportal.DataObjects.StudentCourseDO;
import com.example.Studentdataportal.DataObjects.StudentDO;
import com.example.Studentdataportal.Repositorys.StudentRepository;
import com.example.Studentdataportal.Services.StudentCourseServices;
import com.example.Studentdataportal.Services.StudentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
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
    public ResponseEntity<List<CgpaAndSgpaDO>> getstudentsListShortlistByCGPA(@PathVariable("id") String batch,@PathVariable("id1") Float cgpa)
    {
        List<CgpaAndSgpaDO> cgpaAndSgpaDOList =  studentCourseServices.getstudentsListShortlistByCGPA(batch,cgpa);
        return new ResponseEntity<>(cgpaAndSgpaDOList, HttpStatus.OK);
    }
    @GetMapping("/getstudentsListSortByCGPA/{id}")
    public ResponseEntity<List<CgpaAndSgpaDO>> getstudentsListSortByCGPA(@PathVariable("id") String batch)
    {
        List<CgpaAndSgpaDO> cgpaAndSgpaDOList =  studentCourseServices.getstudentsListSortByCGPA(batch);
        return new ResponseEntity<>(cgpaAndSgpaDOList, HttpStatus.OK);
    }
    @GetMapping("/getstudentsListSortBySGPA/{id}/{id1}")
    public ResponseEntity<List<CgpaAndSgpaDO>> getstudentsListSortBySGPA(@PathVariable("id") String batch,@PathVariable("id1") String sem)
    {
        List<CgpaAndSgpaDO> cgpaAndSgpaDOList =  studentCourseServices.getstudentsListSortBySGPA(batch,sem);
        return new ResponseEntity<>(cgpaAndSgpaDOList, HttpStatus.OK);
    }
}
