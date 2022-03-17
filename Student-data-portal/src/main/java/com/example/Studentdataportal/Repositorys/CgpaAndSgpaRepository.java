package com.example.Studentdataportal.Repositorys;

import com.example.Studentdataportal.Entitis.CgpaAndSgpaEntity;
import com.example.Studentdataportal.Entitis.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CgpaAndSgpaRepository extends JpaRepository<CgpaAndSgpaEntity,Long> {

    boolean existsBystudentid(StudentEntity studentid);
    CgpaAndSgpaEntity getBystudentid(StudentEntity studentid);
}
