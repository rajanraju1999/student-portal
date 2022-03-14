package com.example.Studentdataportal.Util;

import com.example.Studentdataportal.DataObjects.BatchDO;
import com.example.Studentdataportal.DataObjects.StudentDO;
import com.example.Studentdataportal.Entitis.BatchEntity;
import com.example.Studentdataportal.Entitis.StudentEntity;
import com.example.Studentdataportal.Repositorys.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentConvert {

    @Autowired
    BatchRepository batchRepository;
    public StudentEntity convert2StudentEntity(StudentDO studentDO)
    {
       return StudentEntity.builder().rollnumber(studentDO.getId()).emailid(studentDO.getEmailid()).name(studentDO.getName())
                .batchid( batchRepository.getByBatch(studentDO.getBatchid())).section(studentDO.getSection()).yearofjoining(studentDO.getYearofjoining()).build();

    }

    public StudentDO convert2StudentDO(StudentEntity studententity)
    {
        return StudentDO.builder().id(studententity.getRollnumber()).emailid(studententity.getEmailid()).name(studententity.getName())
                .batchid(studententity.getBatchid().getBatch()).section(studententity.getSection()).yearofjoining(studententity.getYearofjoining()).build();

    }

    public BatchEntity convert2BatchEntity(BatchDO batchDO) {

        return BatchEntity.builder().batch(batchDO.getBatch_id()).build();

    }

}
