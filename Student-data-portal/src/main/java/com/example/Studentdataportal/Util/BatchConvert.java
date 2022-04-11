package com.example.Studentdataportal.Util;


import com.example.Studentdataportal.DataObjects.BatchDO;
import com.example.Studentdataportal.DataObjects.CourseDO;
import com.example.Studentdataportal.Entitis.BatchEntity;
import com.example.Studentdataportal.Entitis.CourseEntity;
import org.springframework.stereotype.Component;

@Component
public class BatchConvert {
    public BatchDO convert2BatchDO(BatchEntity batchEntity)
    {
        return BatchDO.builder().batch_id(batchEntity.getBatch()).regulation(batchEntity.getRegulation()).build();
    }
    public BatchEntity convert2BatchEntity(BatchDO batchDO) {

        return BatchEntity.builder().batch(batchDO.getBatch_id()).regulation(batchDO.getRegulation()).build();

    }
}
