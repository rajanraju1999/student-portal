package com.example.Studentdataportal.zattainmentmodule.Repository;

import com.example.Studentdataportal.Entitis.BatchEntity;
import com.example.Studentdataportal.Entitis.CourseEntity;
import com.example.Studentdataportal.Entitis.StudentEntity;
import com.example.Studentdataportal.zattainmentmodule.Entity.AttainmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttainmentRepository extends JpaRepository<AttainmentEntity,Long> {
    boolean existsByBatchidAndCourseid(BatchEntity batchEntity, CourseEntity courseEntity);
    void deleteAllByBatchidAndCourseid(BatchEntity batchEntity, CourseEntity courseEntity);
    List<AttainmentEntity> getAllByBatchid(BatchEntity batchEntity);
    List<AttainmentEntity> getAllByBatchidAndCourseid(BatchEntity batchEntity, CourseEntity courseEntity);

}
