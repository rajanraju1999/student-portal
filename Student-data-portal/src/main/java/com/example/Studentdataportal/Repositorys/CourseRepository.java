package com.example.Studentdataportal.Repositorys;

import com.example.Studentdataportal.Entitis.CourseEntity;
import com.example.Studentdataportal.Entitis.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity,String> {

    CourseEntity getByCourseid(String courseid);

    public boolean existsByCourseName(String courseName);
    CourseEntity getByCourseName(String courseName);


    public boolean existsByCourseid(String courseid);
    public boolean existsByCourseNameAndCourseRegulation(String courseName,String courseregulation);
    
    CourseEntity getByCourseNameAndCourseRegulation(String courseName,String courseregulation);

}
