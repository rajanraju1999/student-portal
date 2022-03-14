package com.example.Studentdataportal.Repositorys;

import com.example.Studentdataportal.Entitis.BatchEntity;
import com.example.Studentdataportal.Entitis.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchRepository extends JpaRepository<BatchEntity,String> {

    BatchEntity getByBatch(String batch);
    boolean existsByBatch(String batch);
}
