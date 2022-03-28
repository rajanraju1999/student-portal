package com.example.Studentdataportal.zattainmentmodule.Util;

import com.example.Studentdataportal.DataObjects.CourseDO;
import com.example.Studentdataportal.Entitis.CourseEntity;
import com.example.Studentdataportal.Repositorys.BatchRepository;
import com.example.Studentdataportal.Repositorys.CourseRepository;
import com.example.Studentdataportal.zattainmentmodule.DataObjects.AttainmentDO;
import com.example.Studentdataportal.zattainmentmodule.Entity.AttainmentEntity;
import com.example.Studentdataportal.zattainmentmodule.Repository.AttainmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AttainmentConvert {
    @Autowired
    BatchRepository batchRepository;
    @Autowired
    CourseRepository courseRepository;
    public AttainmentEntity convert2AttainmentEntity(AttainmentDO attainmentDO)
    {
        return AttainmentEntity.builder().batchid(batchRepository.getByBatch(attainmentDO.getBatchid())).courseid(courseRepository.getByCourseid(attainmentDO.getCourseid()))
                .COtype(attainmentDO.getCOtype()).COvalue(attainmentDO.getCOvalue()).PO1(attainmentDO.getPO1()).PO2(attainmentDO.getPO2())
                .PO3(attainmentDO.getPO3()).PO4(attainmentDO.getPO4()).PO5(attainmentDO.getPO5()).PO6(attainmentDO.getPO6()).PO7(attainmentDO.getPO7())
                .PO8(attainmentDO.getPO8()).PO9(attainmentDO.getPO9()).PO10(attainmentDO.getPO10()).PO11(attainmentDO.getPO11())
                .PO12(attainmentDO.getPO12()).PSO1(attainmentDO.getPSO1()).PSO2(attainmentDO.getPSO2()).build();

    }
}
