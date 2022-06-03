package com.example.Studentdataportal.zattainmentmodule.Repository;

import com.example.Studentdataportal.Entitis.BatchEntity;
import com.example.Studentdataportal.Entitis.CourseEntity;
import com.example.Studentdataportal.zattainmentmodule.Entity.AssignEntity;
import com.example.Studentdataportal.zattainmentmodule.Entity.AttainmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignRepository extends JpaRepository<AssignEntity,Long> {
    boolean existsByBatchidAndCourseid(BatchEntity batchEntity, CourseEntity courseEntity);
    void deleteAllByBatchidAndCourseid(BatchEntity batchEntity, CourseEntity courseEntity);
    List<AssignEntity> getAllByBatchid(BatchEntity batchEntity);
    List<AssignEntity> getAllByBatchidAndCourseid(BatchEntity batchEntity, CourseEntity courseEntity);
    AssignEntity getByBatchidAndCourseid(BatchEntity batchEntity, CourseEntity courseEntity);
    List<AssignEntity> getAllByBatchidAndStatus(BatchEntity batchEntity,String status);
}
