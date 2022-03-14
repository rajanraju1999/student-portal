package com.example.Studentdataportal.Repositorys;

import com.example.Studentdataportal.Entitis.CourseEntity;
import com.example.Studentdataportal.Entitis.StudentCourseEntity;
import com.example.Studentdataportal.Entitis.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourseEntity,Long> {

    StudentCourseEntity findByStudentidAndCourseid(StudentEntity studentId, CourseEntity courseId);

    public  boolean existsByStudentidAndCourseid(StudentEntity studentId, CourseEntity courseId);

    public  boolean existsByStudentid(StudentEntity studentId);
    public  boolean  existsByStudentidAndSemester(StudentEntity studentid, long semester);

    List<StudentCourseEntity>findAllByStudentidAndSemester(StudentEntity studentId,long semester);

    List<StudentCourseEntity>findAllByStudentid(StudentEntity studentid);
    List<StudentCourseEntity>getAllByStudentid(StudentEntity studentid);

    StudentCourseEntity getByStudentidAndCourseid(StudentEntity studentId, CourseEntity courseId);
}
