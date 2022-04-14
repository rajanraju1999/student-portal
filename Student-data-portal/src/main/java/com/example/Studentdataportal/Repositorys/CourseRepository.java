package com.example.Studentdataportal.Repositorys;

import com.example.Studentdataportal.Entitis.CourseEntity;
import com.example.Studentdataportal.Entitis.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity,String> {

    CourseEntity getByCourseid(String courseid);

    public boolean existsByCourseName(String courseName);
    CourseEntity getByCourseName(String courseName);

    List<CourseEntity> getAllByCourseRegulation(String regulation);
    public boolean existsByCourseRegulation(String regulation);
    public boolean existsByCourseid(String courseid);
    public boolean existsByCourseNameAndCourseRegulation(String courseName,String courseregulation);
    
    CourseEntity getByCourseNameAndCourseRegulation(String courseName,String courseregulation);



}
