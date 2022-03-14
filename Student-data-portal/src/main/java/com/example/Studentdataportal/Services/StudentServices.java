package com.example.Studentdataportal.Services;

import com.example.Studentdataportal.DataObjects.BatchDO;
import com.example.Studentdataportal.DataObjects.StudentDO;
import com.example.Studentdataportal.Entitis.BatchEntity;
import com.example.Studentdataportal.Entitis.StudentCourseEntity;
import com.example.Studentdataportal.Entitis.StudentEntity;
import com.example.Studentdataportal.Repositorys.BatchRepository;
import com.example.Studentdataportal.Repositorys.StudentCourseLogRepository;
import com.example.Studentdataportal.Repositorys.StudentCourseRepository;
import com.example.Studentdataportal.Repositorys.StudentRepository;
import com.example.Studentdataportal.Util.Helper;
import com.example.Studentdataportal.Util.StudentConvert;
import com.example.Studentdataportal.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class StudentServices {

    @Autowired
    StudentConvert studentConvert;
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentCourseLogRepository LogRepository;
    @Autowired
    StudentCourseRepository studentCourseRepository;
    @Autowired
    BatchRepository batchRepository;
    public void createstudent(StudentDO studentDO)
    {


        if(studentDO.getId()==null||studentDO.getId().trim().isEmpty()||studentDO.getEmailid()==null||studentDO.getEmailid().trim().isEmpty()||studentDO.getName()==null||studentDO.getName().trim().isEmpty()||studentDO.getBatchid()==null||studentDO.getBatchid().trim().isEmpty()||studentDO.getSection()==null||studentDO.getSection().trim().isEmpty()||studentDO.getYearofjoining()==null||studentDO.getYearofjoining().trim().isEmpty())
        {
            throw new EmptyFieldException();
        }
        //handle field missing exception!
        if(studentRepository.existsById(studentDO.getId()))
        {
            throw new AlreadyStudentExistsException(studentDO.getId());
        }
        if(studentRepository.existsByemailid(studentDO.getEmailid())){

            throw new AlreadyEmailExistsException();
        }

        StudentEntity studentEntity= studentConvert.convert2StudentEntity(studentDO);

            studentRepository.save(studentEntity);

    }

    public void deletestudent(String id)
    {
        if(!studentRepository.existsById(id))
        {
            throw new NoSuchElementException();
        }
        studentRepository.deleteById(id);
    }

    public  List<StudentDO> getallstudents()
    {
        List<StudentEntity> studentsList = studentRepository.findAll();
        List<StudentDO> studentDOsList =new ArrayList<>();
        for(int i=0;i<studentsList.size();i++)
        {
            studentDOsList.add(studentConvert.convert2StudentDO(studentsList.get(i)));
        }
        return studentDOsList;
    }

    public StudentDO getstudentbyid(String id)
    {

        if(!studentRepository.existsById(id))
        {
            throw new NoSuchElementException();
        }

        Optional<StudentEntity> studentEntity = studentRepository.findById(id);
        StudentDO studentDO =  studentConvert.convert2StudentDO(studentEntity.get());

        return studentDO;
    }

    public  List<StudentDO> getAllwithoutbacklogs(String batchid)
    {
        if(!batchRepository.existsByBatch(batchid)){
            throw new BatchDoesNotExistException(batchid);
        }

        List<StudentEntity> batchstudentsList = studentRepository.getByBatchid(batchRepository.getByBatch(batchid));
        List<StudentDO> studentDOsList =new ArrayList<>();
        List<StudentEntity> studentsList=new ArrayList<>();
        for(int i=0;i<batchstudentsList.size();i++){

         if(!LogRepository.existsByStudentid(batchstudentsList.get(i)))
         {
             studentsList.add(batchstudentsList.get(i));
         }
        }
        for(int i=0;i<studentsList.size();i++)
        {
            studentDOsList.add(studentConvert.convert2StudentDO(studentsList.get(i)));
        }
        return studentDOsList;
    }

    public  List<StudentDO> getAllPasswithbacklogs(String batchid)
    {
        if(!batchRepository.existsByBatch(batchid)){
            throw new BatchDoesNotExistException(batchid);
        }
        List<StudentEntity> batchstudentsList = studentRepository.getByBatchid(batchRepository.getByBatch(batchid));
        List<StudentDO> studentDOsList =new ArrayList<>();
        List<StudentCourseEntity> studentsCourseList=new ArrayList<>();
        List<StudentEntity> studentsList=new ArrayList<>();
        for(int i=0;i<batchstudentsList.size();i++){
            if(studentCourseRepository.existsByStudentid(batchstudentsList.get(i))){

                studentsCourseList.addAll(studentCourseRepository.getAllByStudentid(batchstudentsList.get(i)));
            }

            int countcourseatemp1=0,countcourceatemptmorethan1=0;
            for(int j=0; j<studentsCourseList.size(); j++){
                if(!studentsCourseList.get(j).getGrade().equals("F") && studentsCourseList.get(j).getTotalattempts()>1) {
                    countcourceatemptmorethan1++;
                }
                else if(!studentsCourseList.get(j).getGrade().equals("F") && studentsCourseList.get(j).getTotalattempts()==1){
                    countcourseatemp1++;
                }
            }
            if(countcourceatemptmorethan1+countcourseatemp1 == studentsCourseList.size() && countcourceatemptmorethan1!=0){
                studentsList.add(batchstudentsList.get(i));
            }

            studentsCourseList.clear();

        }


        for(int i=0;i<studentsList.size();i++)
        {
            studentDOsList.add(studentConvert.convert2StudentDO(studentsList.get(i)));
        }
        return studentDOsList;
    }


    public  List<StudentDO> getAllstillwithbacklogs(String batchid)
    {
        if(!batchRepository.existsByBatch(batchid)){
            throw new BatchDoesNotExistException(batchid);
        }
        List<StudentEntity> batchstudentsList = studentRepository.getByBatchid(batchRepository.getByBatch(batchid));
        List<StudentDO> studentDOsList =new ArrayList<>();
        List<StudentCourseEntity> studentsCourseList=new ArrayList<>();
        List<StudentEntity> studentsList=new ArrayList<>();
        for(int i=0;i<batchstudentsList.size();i++){
            if(studentCourseRepository.existsByStudentid(batchstudentsList.get(i))){

                studentsCourseList.addAll(studentCourseRepository.getAllByStudentid(batchstudentsList.get(i)));
            }

            int flag=0;
            for(int j=0; j<studentsCourseList.size(); j++){
                if(studentsCourseList.get(j).getGrade().equals("F") ) {
                    flag=1;
                    break;
                }
            }
            if(flag==1){
                studentsList.add(batchstudentsList.get(i));
            }

            studentsCourseList.clear();

        }


        for(int i=0;i<studentsList.size();i++)
        {
            studentDOsList.add(studentConvert.convert2StudentDO(studentsList.get(i)));
        }
        return studentDOsList;
    }



    public void updatestudentbyid(StudentDO studentDO){

        //handle empty feeld

        if(studentDO.getId()==null|| studentDO.getId().isEmpty())
        {
            throw new EmptyRollNumberException();
        }
        if(!studentRepository.existsById(studentDO.getId())) {
            throw new NoSuchElementException();
        }

            StudentEntity studentEntity= studentRepository.getById(studentDO.getId());

            if(studentDO.getName()!=null)
            {
                studentEntity.setName(studentDO.getName());
            }
            if(studentDO.getBatchid()!=null)
            {
                studentEntity.setBatchid(batchRepository.getByBatch(studentDO.getBatchid()));
            }
            if(studentDO.getEmailid()!=null)
            {
                studentEntity.setEmailid(studentDO.getEmailid());
            }
            /*if(studentDO.getYear()!=null)
            {
                studentEntity.setYear(studentDO.getYear());
            }*/
            if(studentDO.getSection()!=null)
            {
                studentEntity.setSection(studentDO.getSection());
            }
            if(studentDO.getYearofjoining()!=null)
            {
                studentEntity.setYearofjoining(studentDO.getYearofjoining());
            }

            studentRepository.save(studentEntity);


    }
    public void savefile(MultipartFile file) {
        try {
            List<StudentEntity> studentEntityList = Helper.studentExcelToDB(file.getInputStream(),studentRepository,batchRepository);
            //  System.out.println(studentCourseEntityList);
            try {
                studentRepository.saveAll(studentEntityList);
            }catch (Exception e){
                throw new EmptyFieldException();
            }

        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    public void createbatch(BatchDO batchDO){


        BatchEntity batchEntity= studentConvert.convert2BatchEntity(batchDO);
        System.out.println(batchEntity);
        batchRepository.save(batchEntity);

    }


}
