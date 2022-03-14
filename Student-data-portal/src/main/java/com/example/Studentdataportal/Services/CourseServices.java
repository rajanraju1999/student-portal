package com.example.Studentdataportal.Services;

import com.example.Studentdataportal.DataObjects.CourseDO;
import com.example.Studentdataportal.Entitis.CourseEntity;
import com.example.Studentdataportal.Repositorys.CourseRepository;
import com.example.Studentdataportal.Util.CourseConvert;
import com.example.Studentdataportal.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CourseServices {
    @Autowired
    CourseConvert courseConvert;
    @Autowired
    CourseRepository courseRepository;

    public void createcourse(CourseDO courseDO)
    {

        if(courseDO.getCourseId()==null||courseDO.getCourseId().trim().isEmpty()||courseDO.getCourseType()==null||courseDO.getCourseType().trim().isEmpty()||courseDO.getCourseName()==null||courseDO.getCourseName().trim().isEmpty()||courseDO.getCourseCredits()==null||courseDO.getCourseCredits().trim().isEmpty()||courseDO.getCourseRegulation()==null||courseDO.getCourseRegulation().trim().isEmpty())
        {
            throw new EmptyFieldException();
        }
        //handle field missing exception!
        if(courseRepository.existsById(courseDO.getCourseId()))
        {
            throw new AlreadyCourseIdExistException();
        }
        if(courseRepository.existsByCourseNameAndCourseRegulation(courseDO.getCourseName(),courseDO.getCourseRegulation()))
        {
            throw new AlreadyCourseNameExistException();
        }
        CourseEntity courseEntity= courseConvert.convert2CourseEntity(courseDO);

        courseRepository.save(courseEntity);

    }
    public void deletecourse(String id)
    {
        if(!courseRepository.existsById(id))
        {
            throw new NoSuchElementException();
        }
        courseRepository.deleteById(id);
    }

    public List<CourseDO> getallcourses()
    {
        List<CourseEntity> coursesList = courseRepository.findAll();
        List<CourseDO> courseDOsList =new ArrayList<>();
        for(int i=0;i<coursesList.size();i++)
        {
            courseDOsList.add(courseConvert.convert2CourseDO(coursesList.get(i)));
        }
        return courseDOsList;
    }

    public CourseDO getcoursesbyid(String id)
    {

        if(!courseRepository.existsById(id))
        {
            throw new NoSuchElementException();
        }

        Optional<CourseEntity> courseEntity = courseRepository.findById(id);
        CourseDO courseDO =  courseConvert.convert2CourseDO(courseEntity.get());

        return courseDO;
    }

    public void updatecoursebyid(CourseDO courseDO){

        //handle empty field

        if(courseDO.getCourseId()==null|| courseDO.getCourseId().isEmpty())
        {
            throw new EmptyRollNumberException();
        }

        if(!courseRepository.existsById(courseDO.getCourseId())) {
            throw new NoSuchElementException();
        }

        CourseEntity courseEntity= courseRepository.getById(courseDO.getCourseId());

        if(courseDO.getCourseName()!=null)
        {
            courseEntity.setCourseName(courseDO.getCourseName());
        }
        if(courseDO.getCourseType()!=null)
        {
            courseEntity.setCourseType(courseDO.getCourseType());
        }
        if(courseDO.getCourseCredits()!=null)
        {
            courseEntity.setCourseCredits(courseDO.getCourseCredits());
        }
        if(courseDO.getCourseRegulation()!=null)
        {
            courseEntity.setCourseRegulation(courseDO.getCourseRegulation());
        }
       courseRepository.save(courseEntity);
    }
}
