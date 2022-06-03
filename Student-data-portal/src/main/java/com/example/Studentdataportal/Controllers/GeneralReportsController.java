package com.example.Studentdataportal.Controllers;

import com.example.Studentdataportal.Entitis.CourseEntity;
import com.example.Studentdataportal.Services.GeneralReportsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/generalreports")
public class GeneralReportsController {
    @Autowired
    GeneralReportsServices generalReportsServices;

    @GetMapping("getallcoursbtbatch/{id}/{id1}")
    public ResponseEntity<?> getallcoursbybatch(@PathVariable("id") String batchid,@PathVariable("id1") Long sem ) {

     // List<CourseEntity>  courseEntityList=





        return new ResponseEntity<>(generalReportsServices.getgeneralreportoverall(batchid,sem),HttpStatus.OK);
    }

    @GetMapping("getallcoursbtbatch/{id}/{id1}/{id2}")
    public ResponseEntity<?> getallcoursbybatch(@PathVariable("id") String batchid,@PathVariable("id1") Long sem,@PathVariable("id2") String section ) {



        return new ResponseEntity<>(generalReportsServices.getgeneralreport(batchid,sem,section),HttpStatus.OK);
    }




}
