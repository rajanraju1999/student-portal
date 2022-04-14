package com.example.Studentdataportal.zattainmentmodule.Controllers;


import com.example.Studentdataportal.DataObjects.CourseDO;
import com.example.Studentdataportal.Repositorys.CourseRepository;
import com.example.Studentdataportal.Services.CourseServices;
import com.example.Studentdataportal.Util.Helper;
import com.example.Studentdataportal.Util.ResponseMessage;
import com.example.Studentdataportal.zattainmentmodule.DataObjects.AttainmentDO;
import com.example.Studentdataportal.zattainmentmodule.DataObjects.AttainmentReportDO;
import com.example.Studentdataportal.zattainmentmodule.Service.AttainmentServices;
import com.example.Studentdataportal.zattainmentmodule.Util.NewHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/attainment")
public class AttainmentController {
    @Autowired
    AttainmentServices attainmentServices;
    
    @GetMapping("/getallattainments/{id}")
    public ResponseEntity<List<AttainmentDO>> getallattinments(@PathVariable("id") String batch)
    {
        List<AttainmentDO> AttainmentDoList;
        AttainmentDoList = attainmentServices.getAllAttinmentsOfBatch(batch);
        return new ResponseEntity<>(AttainmentDoList, HttpStatus.OK);
    }
    

    @GetMapping("/attainmentsreport/{id}")
    public ResponseEntity<List<AttainmentReportDO>> getattinmentreport(@PathVariable("id") String batch)
    {
        List<AttainmentReportDO> AttainmentReportDOList;
        AttainmentReportDOList = attainmentServices.getattainmentreport(batch);
        return new ResponseEntity<>(AttainmentReportDOList, HttpStatus.OK);
    }

    @GetMapping("/getUploadedCourses/{id}")
    public ResponseEntity<List<CourseDO>> getcourses(@PathVariable("id") String batch)
    {
        List<CourseDO> courseDOListDO;
        courseDOListDO = attainmentServices.getuploadedcourses(batch);
        return new ResponseEntity<>(courseDOListDO, HttpStatus.OK);
    }

    @PostMapping("/upload/{id}/{id1}/{id2}")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file,@PathVariable("id") String batch,@PathVariable("id1") String courseName,@PathVariable("id2") String regulation) {
        String message = "";
        if (NewHelper.hasExcelFormat(file)) {

            //try {
            attainmentServices.save(file,batch,courseName,regulation);
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
    @DeleteMapping("/delete/{id}/{id1}/{id2}")
    public ResponseEntity<AttainmentDO> deletebatchcoursetable(@PathVariable("id") String batch, @PathVariable("id1") String courseName, @PathVariable("id2") String regulation)
    {
        attainmentServices.deletebatchcourseregulation(batch,courseName,regulation);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/update/{id}/{id1}/{id2}")
    public ResponseEntity<ResponseMessage> updateFile(@RequestParam("file") MultipartFile file,@PathVariable("id") String batch,@PathVariable("id1") String courseName,@PathVariable("id2") String regulation) {
        String message = "";
        if (NewHelper.hasExcelFormat(file)) {

            //try {
            attainmentServices.deletebatchcourseregulation(batch,courseName,regulation);
            attainmentServices.save(file,batch,courseName,regulation);
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
}
