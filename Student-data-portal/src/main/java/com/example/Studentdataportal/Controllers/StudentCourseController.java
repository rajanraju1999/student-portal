package com.example.Studentdataportal.Controllers;

import com.example.Studentdataportal.DataObjects.LogsDO;
import com.example.Studentdataportal.DataObjects.StudentCourseDO;
import com.example.Studentdataportal.DataObjects.StudentDO;
import com.example.Studentdataportal.Services.StudentCourseServices;
import com.example.Studentdataportal.Util.Helper;
import com.example.Studentdataportal.Util.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@RestController
@RequestMapping("/studentcourse")
public class StudentCourseController {
        @Autowired
        StudentCourseServices studentCourseServices;

    @PostMapping("/uploadsupply/{id}/{id1}/{id2}/{id3}")
    public ResponseEntity<ResponseMessage> uploadSupplyFile(@PathVariable("file") MultipartFile file,@PathVariable("id") Long sem,@PathVariable("id1") String type,@PathVariable("id2")String dateString,@PathVariable("id3")String regulation) {
        String message = "";
        if (Helper.hasExcelFormat(file)) {

            //try {s
            System.out.println(dateString);
            studentCourseServices.saveSupply(file,sem,type, dateString,regulation);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            // } //catch (Exception e) {
            //message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            // e.printStackTrace();
            //return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            // }
        }
        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @PostMapping("/upload/{id}/{id1}/{id2}/{id3}")
    public ResponseEntity<ResponseMessage> uploadFile(@PathVariable("file") MultipartFile file,@PathVariable("id") Long sem,@PathVariable("id1") String type,@PathVariable("id2")String dateString,@PathVariable("id3")String regulation) {
        String message = "";
        if (Helper.hasExcelFormat(file)) {

            //try {s
            System.out.println(dateString);
            studentCourseServices.save(file,sem,type, dateString,regulation);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            // } //catch (Exception e) {
            //message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            // e.printStackTrace();
            //return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            // }
        }
        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @PostMapping("/advancesupply/upload/{id}/{id1}/{id2}/{id3}")
    public ResponseEntity<ResponseMessage> advsupuploadFile(@PathVariable("file") MultipartFile file,@PathVariable("id") Long sem,@PathVariable("id1") String type,@PathVariable("id2")String dateString,@PathVariable("id3")String regulation) {
        String message = "";
        if (Helper.hasExcelFormat(file)) {

            //try {s
            System.out.println(dateString);
            studentCourseServices.saveadvsup(file,sem,type, dateString,regulation);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            // } //catch (Exception e) {
            //message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            // e.printStackTrace();
            //return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            // }
        }
        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }


    @PutMapping("/updatestudentcourse")
    public ResponseEntity<ResponseMessage> updateStudentCourse(@RequestBody StudentCourseDO studentCourseDO){
        studentCourseServices.updateStudentCourse(studentCourseDO);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/getAll/{id}")
        public ResponseEntity<List<StudentCourseDO>> getAllbybatchid(@PathVariable String id) {

                List<StudentCourseDO> studentCourseDOList = studentCourseServices. getAllbybatchid(id);
                if (studentCourseDOList.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                return new ResponseEntity<>(studentCourseDOList, HttpStatus.OK);

            }

    @GetMapping("/getAll/{id}/{id1}/{id2}")
    public ResponseEntity<List<StudentCourseDO>> getAllbybatchidAndSection(@PathVariable String id,@PathVariable Long id1,@PathVariable String id2) {

        List<StudentCourseDO> studentCourseDOList = studentCourseServices.getAllbybatchidandsectionandsemester(id,id2,id1);
        if (studentCourseDOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(studentCourseDOList, HttpStatus.OK);

    }
    @GetMapping("/getAll/{id}/{id1}")
    public ResponseEntity<List<StudentCourseDO>> getAllbybatchidandsemester(@PathVariable String id,@PathVariable Long id1) {

        List<StudentCourseDO> studentCourseDOList = studentCourseServices.getAllbybatchidandsemester(id,id1);
        if (studentCourseDOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(studentCourseDOList, HttpStatus.OK);

    }

    @GetMapping("/getAllById/{id}")
    public ResponseEntity<List<StudentCourseDO>> getAll(@PathVariable String id) {

        List<StudentCourseDO> studentCourseDOList = studentCourseServices.getAllbyid(id);
        if (studentCourseDOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(studentCourseDOList, HttpStatus.OK);

    }


    @GetMapping("/getAllByidAndSem/{id}/{id2}")
    public ResponseEntity<List<StudentCourseDO>> getAllByIdAndSem(@PathVariable String id,@PathVariable long id2) {

        List<StudentCourseDO> studentCourseDOList = studentCourseServices.getAllByIdAndSem(id,id2);
        if (studentCourseDOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(studentCourseDOList, HttpStatus.OK);

    }

    @GetMapping("/getlogs/{id}/{id2}")
    public ResponseEntity<List<LogsDO>> getlogs(@PathVariable String id, @PathVariable String id2) {

        List<LogsDO> logsDOList = studentCourseServices.getlogs(id,id2);
        if (logsDOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(logsDOList, HttpStatus.OK);

    }




}
