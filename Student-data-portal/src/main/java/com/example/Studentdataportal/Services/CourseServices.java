package com.example.Studentdataportal.Services;

import com.example.Studentdataportal.DataObjects.CourseDO;
import com.example.Studentdataportal.DataObjects.RegulationDO;
import com.example.Studentdataportal.DataObjects.StudentDO;
import com.example.Studentdataportal.Entitis.CourseEntity;
import com.example.Studentdataportal.Entitis.StudentCourseEntity;
import com.example.Studentdataportal.Entitis.StudentEntity;
import com.example.Studentdataportal.Repositorys.BatchRepository;
import com.example.Studentdataportal.Repositorys.CourseRepository;
import com.example.Studentdataportal.Repositorys.StudentCourseRepository;
import com.example.Studentdataportal.Repositorys.StudentRepository;
import com.example.Studentdataportal.Util.CourseConvert;
import com.example.Studentdataportal.exception.*;
import com.example.Studentdataportal.zattainmentmodule.DataObjects.AssignDO;
import com.example.Studentdataportal.zattainmentmodule.Entity.AssignEntity;
import com.example.Studentdataportal.zattainmentmodule.Repository.AssignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseServices {
    @Autowired
    CourseConvert courseConvert;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    StudentCourseRepository studentCourseRepository;
    @Autowired
    BatchRepository batchRepository;
    @Autowired
    AssignRepository assignRepository;

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
    public List<RegulationDO> getallregulations()
    {
        List<CourseEntity> coursesList = courseRepository.findAll();
        List<RegulationDO> regulationDOList =new ArrayList<>();
        Set<RegulationDO> regulationDOset = new HashSet<RegulationDO>();
        for(int i=0;i<coursesList.size();i++)
        {
            regulationDOset.add(courseConvert.convert2RegulationDO(coursesList.get(i)));
        }
        for(RegulationDO i: regulationDOset){
            regulationDOList.add(i);
        }
        return regulationDOList;
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
    public List<CourseDO> getallcoursesbyreg(String reg)
    {

        if(!courseRepository.existsByCourseRegulation(reg))
        {
            throw new NoSuchElementException();
        }

        List<CourseEntity> courseEntityList = courseRepository.getAllByCourseRegulation(reg);
        List<CourseDO> courseDOList=new ArrayList<>();
        for(int h=0; h<courseEntityList.size() ; h++) {
            courseDOList.add(courseConvert.convert2CourseDO(courseEntityList.get(h)));
        }
        return courseDOList;
    }

    public List<CourseDO> coursesToAssign(String batch){
        Set<CourseDO> courses_Set = new HashSet<CourseDO>();

        List<CourseEntity> courseEntityList = courseRepository.getAllByCourseRegulation(batchRepository.getByBatch(batch).getRegulation());
        for(int i=0; i<courseEntityList.size() ; i++){
            courses_Set.add(courseConvert.convert2CourseDO(courseEntityList.get(i)));
        }

        List<AssignEntity> assignEntityList = assignRepository.getAllByBatchid(batchRepository.getByBatch(batch));
        for(int i=0; i<assignEntityList.size() ; i++){
            courses_Set.remove(assignEntityList.get(i).getCourseid());
        }

        List<CourseDO> courseDOList = new ArrayList<>();
        for(CourseDO i: courses_Set){
            courseDOList.add(i);
        }
        return courseDOList;
    }
    public List<CourseDO> coursesToExtendTime(String batch){
        List<AssignEntity> assignEntityList = assignRepository.getAllByBatchidAndStatus(batchRepository.getByBatch(batch),"NA");
        List<CourseDO> courseDOList = new ArrayList<>();
        for(int i=0; i<assignEntityList.size() ; i++){
            courseDOList.add(courseConvert.convert2CourseDO(assignEntityList.get(i).getCourseid()));
        }
        return courseDOList;
    }

    public List<CourseDO> coursesToDeleteAttainment(String batch){
        List<AssignEntity> assignEntityList = assignRepository.getAllByBatchidAndStatus(batchRepository.getByBatch(batch),"UPLOADED");
        List<CourseDO> courseDOList = new ArrayList<>();
        for(int i=0; i<assignEntityList.size() ; i++){
            courseDOList.add(courseConvert.convert2CourseDO(assignEntityList.get(i).getCourseid()));
        }
        return courseDOList;
    }

    public List<CourseDO> uploadedCourses(String batch)
    {

        /*if(!courseRepository.existsByCourseRegulation(reg))
        {
            throw new NoSuchElementException();
        }*/

        List<StudentEntity> studentEntities = studentRepository.getAllByBatchid(batchRepository.getByBatch(batch));
        List<StudentCourseEntity> studentCourseEntityList = new ArrayList<>();
        for(int i=0; i<studentEntities.size(); i++){
            studentCourseEntityList.addAll(studentCourseRepository.getAllByStudentid(studentEntities.get(i)));
        }
        Set<CourseDO> courses_Set = new HashSet<CourseDO>();
        for(int i=0 ;i<studentCourseEntityList.size(); i++){
            courses_Set.add(courseConvert.convert2CourseDO(studentCourseEntityList.get(i).getCourseid()));
        }
        List<CourseDO> courseDOList = new ArrayList<>();
        for(CourseDO i: courses_Set){
            courseDOList.add(i);
        }
        return courseDOList;
    }
}
