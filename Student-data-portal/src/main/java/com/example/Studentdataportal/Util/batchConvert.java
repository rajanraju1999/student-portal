package com.example.Studentdataportal.Util;


import com.example.Studentdataportal.DataObjects.BatchDO;
import com.example.Studentdataportal.DataObjects.CourseDO;
import com.example.Studentdataportal.Entitis.BatchEntity;
import com.example.Studentdataportal.Entitis.CourseEntity;
import org.springframework.stereotype.Component;

@Component
public class batchConvert {
    public BatchDO convert2BatchDO(BatchEntity batchEntity)
    {
        return BatchDO.builder().batch_id(batchEntity.getBatch()).build();
    }
}
