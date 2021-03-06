package com.example.Studentdataportal.Controllers;

import com.example.Studentdataportal.DataObjects.CourseDO;
import com.example.Studentdataportal.DataObjects.RegulationDO;
import com.example.Studentdataportal.DataObjects.StudentDO;
import com.example.Studentdataportal.Services.CourseServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    CourseServices courseServices;

    @PostMapping("/createcourse")
    public ResponseEntity<CourseDO> createcourse(@RequestBody CourseDO courseDO)
    {
        courseServices.createcourse(courseDO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CourseDO> deletecourse(@PathVariable String id)
    {
        courseServices.deletecourse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getallregulations")
    public ResponseEntity<List<RegulationDO>> getallregulations()
    {
        List<RegulationDO> regulationDOList;
        regulationDOList = courseServices.getallregulations();
        return new ResponseEntity<>(regulationDOList ,HttpStatus.OK);
    }

    @GetMapping("/getallcourses")
    public ResponseEntity<List<CourseDO>> getallcourses()
    {
        List<CourseDO> courseDOsList;
       courseDOsList = courseServices.getallcourses();
        return new ResponseEntity<>(courseDOsList ,HttpStatus.OK);
    }

    @GetMapping("/getallcoursesbyregulation/{reg}")
    public ResponseEntity<List<CourseDO>> getallcoursesbyregulation(@PathVariable String reg)
    {
        List<CourseDO> courseDOsList;
        courseDOsList = courseServices.getallcoursesbyreg(reg);
        return new ResponseEntity<>(courseDOsList ,HttpStatus.OK);
    }


    @GetMapping("/getcourse/{id}")
    public ResponseEntity<CourseDO> getcoursesbyid(@PathVariable String id)
    {
        CourseDO courseDO=  courseServices.getcoursesbyid(id);
        return new ResponseEntity<>(courseDO,HttpStatus.OK);
    }
    @PutMapping("/update")
    public  ResponseEntity<CourseDO> updatecoursebyid(@RequestBody CourseDO courseDO){

        courseServices.updatecoursebyid(courseDO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/coursesToAssign/{batch}")
    public List<CourseDO> coursesToAssign(@PathVariable String batch){
        List<CourseDO> courseDOList =  courseServices.coursesToAssign(batch);
        return courseDOList;
    }

    @GetMapping("/coursesToExtendTime/{batch}")
    public List<CourseDO> coursesToExtendTime(@PathVariable String batch){
        List<CourseDO> courseDOList =  courseServices.coursesToExtendTime(batch);
        return courseDOList;
    }

    @GetMapping("/coursesToDeleteAttainment/{batch}")
    public List<CourseDO> coursesToDeleteAttainment(@PathVariable String batch){
        List<CourseDO> courseDOList =  courseServices.coursesToDeleteAttainment(batch);
        return courseDOList;
    }

    @GetMapping("/getAllResultsUploadedCourses/{batch}")
    public ResponseEntity<List<CourseDO>> getAllResultsUploadedCourses(@PathVariable String batch)
    {
        List<CourseDO> courseDOList = courseServices.uploadedCourses(batch);
        return new ResponseEntity<>(courseDOList,HttpStatus.OK);
    }
}
