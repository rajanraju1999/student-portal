package com.example.Studentdataportal.Controllers;


import com.example.Studentdataportal.DataObjects.BatchReportDO;
import com.example.Studentdataportal.DataObjects.CgpaAndSgpaDO;
import com.example.Studentdataportal.DataObjects.CourseDO;
import com.example.Studentdataportal.Services.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/batch")
public class BatchReportController {

@Autowired
BatchService batchService;
    @GetMapping("/batchreport/{id}")
    public ResponseEntity<BatchReportDO> batchreport(@PathVariable String id )
    {
        BatchReportDO batchReportDO;
        batchReportDO = batchService.getbatchreport(id);
        return new ResponseEntity<>(batchReportDO, HttpStatus.OK);
    }


}
