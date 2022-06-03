package com.example.Studentdataportal.zattainmentmodule.Util;


import com.example.Studentdataportal.Repositorys.BatchRepository;
import com.example.Studentdataportal.Repositorys.CourseRepository;
import com.example.Studentdataportal.zattainmentmodule.DataObjects.AssignDO;
import com.example.Studentdataportal.zattainmentmodule.DataObjects.AttainmentDO;
import com.example.Studentdataportal.zattainmentmodule.Entity.AssignEntity;
import com.example.Studentdataportal.zattainmentmodule.Entity.AttainmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssignConvert {
    @Autowired
    BatchRepository batchRepository;
    @Autowired
    CourseRepository courseRepository;
    public AssignEntity convert2AssignEntity(AssignDO assignDO)
    {
        return AssignEntity.builder().batchid(batchRepository.getByBatch(assignDO.getBatch())).courseid(courseRepository.getByCourseid(assignDO.getCourseId()))
                .date(assignDO.getDate()).time(assignDO.getTime()).status("NA").build();
    }
    public AssignDO convert2AssignDO(AssignEntity assignEntity)
    {
        return AssignDO.builder().batch(assignEntity.getBatchid().getBatch()).courseId(assignEntity.getCourseid().getCourseid()).date(assignEntity.getDate()).time(assignEntity.getTime()).status(assignEntity.getStatus()).build();
    }
}
