package com.example.Studentdataportal.Controllers;

import com.example.Studentdataportal.DataObjects.CourseDO;
import com.example.Studentdataportal.DataObjects.StudentDO;
import com.example.Studentdataportal.Services.CourseServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

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


    @GetMapping("/getallcourses")
    public ResponseEntity<List<CourseDO>> getallcourses()
    {
        List<CourseDO> courseDOsList;
       courseDOsList = courseServices.getallcourses();
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


}
