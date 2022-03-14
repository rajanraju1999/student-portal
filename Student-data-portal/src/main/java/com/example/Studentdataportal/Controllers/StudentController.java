package com.example.Studentdataportal.Controllers;

import com.example.Studentdataportal.DataObjects.BatchDO;
import com.example.Studentdataportal.DataObjects.LogsDo;
import com.example.Studentdataportal.DataObjects.StudentDO;
import com.example.Studentdataportal.Services.StudentServices;
import com.example.Studentdataportal.Util.Helper;
import com.example.Studentdataportal.Util.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentServices studentservices;



    @PostMapping("/create")
    public ResponseEntity<?> createstudent(@RequestBody StudentDO studentDO)
    {
           studentservices.createstudent(studentDO);
           return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<StudentDO> deletestudent(@PathVariable String id)
    {
        studentservices.deletestudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getallstudents")
    public ResponseEntity<List<StudentDO>> getallstudents()
    {
        List<StudentDO> studentDOsList;
        studentDOsList = studentservices.getallstudents();
        return new ResponseEntity<>(studentDOsList,HttpStatus.OK);
    }


    @GetMapping("/getstudent/{id}")
    public ResponseEntity< StudentDO> getstudentbyid(@PathVariable String id)
    {
        StudentDO studentDO=  studentservices.getstudentbyid(id);
        return new ResponseEntity<>(studentDO,HttpStatus.OK);
    }

     @PutMapping("/update")
     public  ResponseEntity<StudentDO> updatestudentbyid(@RequestBody StudentDO studentDO){

         studentservices.updatestudentbyid(studentDO);
         return new ResponseEntity<>(HttpStatus.OK);
     }



    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        if (Helper.hasExcelFormat(file)) {

            //try {
            studentservices.savefile(file);
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


    @GetMapping("/withoutbacklogs/{id}")
    public ResponseEntity<List<StudentDO>> withoutbacklogs(@PathVariable String id) {

        List<StudentDO> studentDOList = studentservices.getAllwithoutbacklogs(id);
        if (studentDOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(studentDOList, HttpStatus.OK);
    }

    @GetMapping("/passedwithbacklogs/{id}")
    public ResponseEntity<List<StudentDO>> Passwithbacklogs(@PathVariable String id) {

        List<StudentDO> studentDOList = studentservices.getAllPasswithbacklogs(id);
        if (studentDOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(studentDOList, HttpStatus.OK);

    }



    @GetMapping("/stillwithbacklogs/{id}")
    public ResponseEntity<List<StudentDO>> stillwithbacklogs(@PathVariable String id) {

        List<StudentDO> studentDOList = studentservices.getAllstillwithbacklogs(id);
        if (studentDOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(studentDOList, HttpStatus.OK);

    }

    @PostMapping("/createbatch")
    public ResponseEntity<BatchDO> createbatch(@RequestBody BatchDO batchDO){

        studentservices.createbatch(batchDO);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}