package com.example.Studentdataportal.Repositorys;


import com.example.Studentdataportal.Entitis.CourseEntity;
import com.example.Studentdataportal.Entitis.StudentCourseLogEntity;
import com.example.Studentdataportal.Entitis.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCourseLogRepository extends JpaRepository<StudentCourseLogEntity,Long> {

   void deleteByStudentidAndCourseid(StudentEntity studentEntity, CourseEntity courseEntity);
   Long countByStudentidAndCourseid(StudentEntity studentEntity, CourseEntity courseEntity);
   List<StudentCourseLogEntity> findAllByStudentidAndCourseid(StudentEntity studentEntity, CourseEntity courseEntity);
   boolean existsByStudentidAndCourseid(StudentEntity studentEntity, CourseEntity courseEntity);
   boolean existsByStudentid(StudentEntity studentEntity);

}
