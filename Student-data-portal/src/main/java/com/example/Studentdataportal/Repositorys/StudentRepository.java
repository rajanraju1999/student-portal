package com.example.Studentdataportal.Repositorys;

import com.example.Studentdataportal.Entitis.BatchEntity;
import com.example.Studentdataportal.Entitis.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity,String> {

   // public void findByrollnumber(String id);
   public boolean existsByemailid(String emailid);

   public boolean existsByRollnumber(String rollnumber);

   StudentEntity getByRollnumber(String rollnumber);

   List<StudentEntity> getByBatchid(BatchEntity batchid);
}
