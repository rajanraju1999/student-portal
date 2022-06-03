package com.example.Studentdataportal.Services;

import com.example.Studentdataportal.DataObjects.*;
import com.example.Studentdataportal.Entitis.*;
import com.example.Studentdataportal.Repositorys.*;
import com.example.Studentdataportal.Util.*;
import com.example.Studentdataportal.exception.EmptyFieldException;
import com.example.Studentdataportal.exception.MultipleAttemptsPresentException;
import com.example.Studentdataportal.exception.NoDataSentException;
import com.example.Studentdataportal.exception.noDataAvailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

@Service
public class StudentCourseServices {
        @Autowired
        StudentCourseRepository studentCourseRepository;
        @Autowired
        StudentCourseConvert studentCourseConvert;
        @Autowired
        StudentConvert studentConvert;
        @Autowired
        StudentRepository studentRepository;
        @Autowired
        BatchRepository batchRepository;
        @Autowired
        CourseRepository courseRepository;
        @Autowired
        CgpaAndSgpaRepository cgpaAndSgpaRepository;
        @Autowired
         StudentCourseLogRepository studentCourseLogRepository;
        @Autowired
        LogsConvert logsConvert;
        @Autowired
        CgpaAndSgpaConvert cgpaAndSgpaConver;


        public void save(MultipartFile file, Long sem, String type,String date,String regulation) {

            try {
                List<StudentCourseDO> studentCourseDoList = Helper.excelToDb(file.getInputStream(),studentRepository,courseRepository,studentCourseRepository,sem,type, date,regulation);



                List<StudentCourseEntity> studentCourseEntityList = new ArrayList<>();
                for(int i=0;i<studentCourseDoList.size();i++) {
                    studentCourseEntityList.add(studentCourseConvert.convert2StudentCourseEntity(studentCourseDoList.get(i)));
                }

                for(int i=0;i<studentCourseEntityList.size();i++){
                    if(studentCourseEntityList.get(i).getGrade().equals("F"))
                    {
                        StudentEntity studentid= studentRepository.getByRollnumber(studentCourseEntityList.get(i).getStudentid().getRollnumber());
                        CourseEntity courseid=courseRepository.getByCourseNameAndCourseRegulation(studentCourseEntityList.get(i).getCourseid().getCourseName(),regulation);

                      StudentCourseLogEntity studentCourseLogEntity=  StudentCourseLogEntity.builder().courseid(courseid).studentid(studentid).attemptdate(date).attemptnumber((long)1).grade("F").build();

                        studentCourseLogRepository.save(studentCourseLogEntity);

                    }

                }
                for(int i=0;i<studentCourseEntityList.size();i++)
                {
                   studentCourseEntityList.get(i).setTotalattempts(1);
                }
                studentCourseRepository.saveAll(studentCourseEntityList);



                //puting cgpa And sgpa
                List<StudentCourseDO> studentCourseDoList1= new ArrayList<>();
                for(int i=0;i<studentCourseDoList.size()-1;i++){
                    if(studentCourseDoList.get(i).getStudentid()!= studentCourseDoList.get(i+1).getStudentid()){
                        studentCourseDoList1.add(studentCourseDoList.get(i));
                    }
                }
                studentCourseDoList1.add(studentCourseDoList.get(studentCourseDoList.size()-1));
                System.out.println(studentCourseDoList1);
                for(int i=0;i<studentCourseDoList1.size();i++) {

                    CgpaAndSgpaEntity cgpaAndSgpaEntity = new CgpaAndSgpaEntity();

                    cgpaAndSgpaEntity.setStudentid(studentRepository.getByRollnumber(studentCourseDoList1.get(i).getStudentid()));
                    if (sem == 1) {
                        cgpaAndSgpaEntity.setSgpa1(studentCourseDoList1.get(i).getSgpa());
                    }
                    if (sem == 2) {
                        cgpaAndSgpaEntity.setSgpa2(studentCourseDoList1.get(i).getSgpa());
                    }
                    if (sem == 3) {
                        cgpaAndSgpaEntity.setSgpa3(studentCourseDoList1.get(i).getSgpa());
                    }
                    if (sem == 4) {
                        cgpaAndSgpaEntity.setSgpa4(studentCourseDoList1.get(i).getSgpa());
                    }
                    if (sem == 5) {
                        cgpaAndSgpaEntity.setSgpa5(studentCourseDoList1.get(i).getSgpa());
                    }
                    if (sem == 6) {
                        cgpaAndSgpaEntity.setSgpa6(studentCourseDoList1.get(i).getSgpa());
                    }
                    if (sem == 7) {
                        cgpaAndSgpaEntity.setSgpa7(studentCourseDoList1.get(i).getSgpa());
                    }
                    if (sem == 8) {
                        cgpaAndSgpaEntity.setSgpa8(studentCourseDoList1.get(i).getSgpa());
                    }
                    cgpaAndSgpaEntity.setCgpa(studentCourseDoList1.get(i).getCgpa());
                    if(!cgpaAndSgpaRepository.existsBystudentid(cgpaAndSgpaEntity.getStudentid())) {
                        cgpaAndSgpaRepository.save(cgpaAndSgpaEntity);
                    }
                    else
                    {
                        CgpaAndSgpaEntity cgpaAndSgpaEntity2=  cgpaAndSgpaRepository.getBystudentid(cgpaAndSgpaEntity.getStudentid());
                        if (sem == 1) {
                            cgpaAndSgpaEntity2.setSgpa1(cgpaAndSgpaEntity.getSgpa1());
                        }
                        if (sem == 2) {
                            cgpaAndSgpaEntity2.setSgpa2(cgpaAndSgpaEntity.getSgpa2());
                        }
                        if (sem == 3) {
                            cgpaAndSgpaEntity2.setSgpa2(cgpaAndSgpaEntity.getSgpa3());
                        }
                        if (sem == 4) {
                            cgpaAndSgpaEntity2.setSgpa4(cgpaAndSgpaEntity.getSgpa4());
                        }
                        if (sem == 5) {
                            cgpaAndSgpaEntity2.setSgpa5(cgpaAndSgpaEntity.getSgpa5());
                        }
                        if (sem == 6) {
                            cgpaAndSgpaEntity2.setSgpa6(cgpaAndSgpaEntity.getSgpa6());
                        }
                        if (sem == 7) {
                            cgpaAndSgpaEntity2.setSgpa7(cgpaAndSgpaEntity.getSgpa7());
                        }
                        if (sem == 8) {
                            cgpaAndSgpaEntity2.setSgpa8(cgpaAndSgpaEntity.getSgpa8());
                        }
                        cgpaAndSgpaEntity2.setCgpa(cgpaAndSgpaEntity.getCgpa());
                        cgpaAndSgpaRepository.save(cgpaAndSgpaEntity2);
                    }

                }




            } catch (IOException e) {
                throw new RuntimeException("fail to store excel data: " + e.getMessage());
            }
        }

    public void save(MultipartFile file, String batch,Long sem,String date,String regulation) {

        try {

            List<StudentCourseDO> studentCourseDoList = Helper.excelToDb(file.getInputStream(),studentRepository,courseRepository,studentCourseRepository,batchRepository,batch,sem,date,regulation);



            List<StudentCourseEntity> studentCourseEntityList = new ArrayList<>();
            for(int i=0;i<studentCourseDoList.size();i++) {
                studentCourseEntityList.add(studentCourseConvert.convert2StudentCourseEntity(studentCourseDoList.get(i)));
            }

            for(int i=0;i<studentCourseEntityList.size();i++){
                if(studentCourseEntityList.get(i).getGrade().equals("F"))
                {
                    StudentEntity studentid= studentRepository.getByRollnumber(studentCourseEntityList.get(i).getStudentid().getRollnumber());
                    CourseEntity courseid=courseRepository.getByCourseNameAndCourseRegulation(studentCourseEntityList.get(i).getCourseid().getCourseName(),regulation);

                    StudentCourseLogEntity studentCourseLogEntity=  StudentCourseLogEntity.builder().courseid(courseid).studentid(studentid).attemptdate(date).attemptnumber((long)1).grade("F").build();

                    studentCourseLogRepository.save(studentCourseLogEntity);

                }

            }
            for(int i=0;i<studentCourseEntityList.size();i++)
            {
                studentCourseEntityList.get(i).setTotalattempts(1);
            }
            studentCourseRepository.saveAll(studentCourseEntityList);



            //puting cgpa And sgpa
            List<StudentCourseDO> studentCourseDoList1= new ArrayList<>();
            for(int i=0;i<studentCourseDoList.size()-1;i++){
                if(studentCourseDoList.get(i).getStudentid()!= studentCourseDoList.get(i+1).getStudentid()){
                    studentCourseDoList1.add(studentCourseDoList.get(i));
                }
            }
            studentCourseDoList1.add(studentCourseDoList.get(studentCourseDoList.size()-1));
            System.out.println(studentCourseDoList1);
            for(int i=0;i<studentCourseDoList1.size();i++) {

                CgpaAndSgpaEntity cgpaAndSgpaEntity = new CgpaAndSgpaEntity();

                cgpaAndSgpaEntity.setStudentid(studentRepository.getByRollnumber(studentCourseDoList1.get(i).getStudentid()));
                if (sem == 1) {
                    cgpaAndSgpaEntity.setSgpa1(studentCourseDoList1.get(i).getSgpa());
                }
                if (sem == 2) {
                    cgpaAndSgpaEntity.setSgpa2(studentCourseDoList1.get(i).getSgpa());
                }
                if (sem == 3) {
                    cgpaAndSgpaEntity.setSgpa3(studentCourseDoList1.get(i).getSgpa());
                }
                if (sem == 4) {
                    cgpaAndSgpaEntity.setSgpa4(studentCourseDoList1.get(i).getSgpa());
                }
                if (sem == 5) {
                    cgpaAndSgpaEntity.setSgpa5(studentCourseDoList1.get(i).getSgpa());
                }
                if (sem == 6) {
                    cgpaAndSgpaEntity.setSgpa6(studentCourseDoList1.get(i).getSgpa());
                }
                if (sem == 7) {
                    cgpaAndSgpaEntity.setSgpa7(studentCourseDoList1.get(i).getSgpa());
                }
                if (sem == 8) {
                    cgpaAndSgpaEntity.setSgpa8(studentCourseDoList1.get(i).getSgpa());
                }
                cgpaAndSgpaEntity.setCgpa(studentCourseDoList1.get(i).getCgpa());
                if(!cgpaAndSgpaRepository.existsBystudentid(cgpaAndSgpaEntity.getStudentid())) {
                    cgpaAndSgpaRepository.save(cgpaAndSgpaEntity);
                }
                else
                {
                    CgpaAndSgpaEntity cgpaAndSgpaEntity2=  cgpaAndSgpaRepository.getBystudentid(cgpaAndSgpaEntity.getStudentid());
                    if (sem == 1) {
                        cgpaAndSgpaEntity2.setSgpa1(cgpaAndSgpaEntity.getSgpa1());
                    }
                    if (sem == 2) {
                        cgpaAndSgpaEntity2.setSgpa2(cgpaAndSgpaEntity.getSgpa2());
                    }
                    if (sem == 3) {
                        cgpaAndSgpaEntity2.setSgpa2(cgpaAndSgpaEntity.getSgpa3());
                    }
                    if (sem == 4) {
                        cgpaAndSgpaEntity2.setSgpa4(cgpaAndSgpaEntity.getSgpa4());
                    }
                    if (sem == 5) {
                        cgpaAndSgpaEntity2.setSgpa5(cgpaAndSgpaEntity.getSgpa5());
                    }
                    if (sem == 6) {
                        cgpaAndSgpaEntity2.setSgpa6(cgpaAndSgpaEntity.getSgpa6());
                    }
                    if (sem == 7) {
                        cgpaAndSgpaEntity2.setSgpa7(cgpaAndSgpaEntity.getSgpa7());
                    }
                    if (sem == 8) {
                        cgpaAndSgpaEntity2.setSgpa8(cgpaAndSgpaEntity.getSgpa8());
                    }
                    cgpaAndSgpaEntity2.setCgpa(cgpaAndSgpaEntity.getCgpa());
                    cgpaAndSgpaRepository.save(cgpaAndSgpaEntity2);
                }

            }




        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    public List<StudentDO> checkStudents(MultipartFile file, String batch,String regulation) {

        try {

            List<StudentDO> studentDoList = Helper.excelToDbcheckStudents(file.getInputStream(),studentRepository,courseRepository,studentCourseRepository,studentConvert,batch,regulation);
            return studentDoList;

        } catch (IOException e) {
            throw new RuntimeException("something went wrong . Try again. " + e.getMessage());
        }
    }


    public List<StudentDO> checkStudents1(MultipartFile file, String batch,String regulation) {

        try {

            List<StudentDO> studentDoList = Helper.excelToDbcheckStudents1(file.getInputStream(),studentRepository,courseRepository,studentCourseRepository,studentConvert,batchRepository,batch,regulation);
            return studentDoList;

        } catch (IOException e) {
            throw new RuntimeException("something went wrong . Try again. " + e.getMessage());
        }
    }


    @Transactional
    public void saveadvsup(MultipartFile file, Long sem, String type,String data,String regulation) {
        try {
            List<StudentCourseDO> studentCourseDoList  = Helper.excelToDbadvsupAndsup(file.getInputStream(),studentRepository,courseRepository,studentCourseRepository,sem,type, data,regulation);
            //  System.out.println(studentCourseEntityList);


            List<StudentCourseEntity> studentCourseEntityList = new ArrayList<>();
            for(int i=0;i<studentCourseDoList.size();i++) {
                studentCourseEntityList.add(studentCourseConvert.convert2StudentCourseEntity(studentCourseDoList.get(i)));
            }

            for(int i=0;i<studentCourseEntityList.size();i++){
                if(!studentCourseEntityList.get(i).getGrade().equals("F"))
                {
                    StudentEntity studentid= studentRepository.getByRollnumber(studentCourseEntityList.get(i).getStudentid().getRollnumber());
                    CourseEntity courseid=courseRepository.getByCourseNameAndCourseRegulation(studentCourseEntityList.get(i).getCourseid().getCourseName(),regulation);

                    if(studentCourseRepository.existsByStudentidAndCourseid(studentid,courseid)){
                       try {
                           if(studentCourseRepository.getByStudentidAndCourseid(studentid,courseid).getGrade().equals("F"))
                           {
                           }
                           //studentCourseLogRepository.deleteByStudentidAndCourseid(studentid,courseid);
                       }catch (Exception e){
                           throw new MultipleAttemptsPresentException();
                       }


                    }
                }

            }

            for(int i=0;i<studentCourseEntityList.size();i++){
                if(!studentCourseEntityList.get(i).getGrade().equals("F"))
                {
                    StudentEntity studentid= studentRepository.getByRollnumber(studentCourseEntityList.get(i).getStudentid().getRollnumber());
                    CourseEntity courseid=courseRepository.getByCourseNameAndCourseRegulation(studentCourseEntityList.get(i).getCourseid().getCourseName(),regulation);

                    if(studentCourseRepository.existsByStudentidAndCourseid(studentid,courseid)){
                            if(studentCourseRepository.getByStudentidAndCourseid(studentid,courseid).getGrade().equals("F"))
                            studentCourseLogRepository.deleteByStudentidAndCourseid(studentid,courseid);
                    }
                }

            }

            for(int i=0; i<studentCourseEntityList.size() ; i++){
                StudentCourseEntity studentCourseEntity = studentCourseRepository.getByStudentidAndCourseid(studentCourseEntityList.get(i).getStudentid(),studentCourseEntityList.get(i).getCourseid());
                studentCourseEntity.setGrade(studentCourseEntityList.get(i).getGrade());
                studentCourseEntity.setSemester(studentCourseEntityList.get(i).getSemester());
                studentCourseEntity.setExamdate(studentCourseEntityList.get(i).getExamdate());
                studentCourseRepository.save(studentCourseEntity);
            }



            //puting cgpa And sgpa
            List<StudentCourseDO> studentCourseDoList1= new ArrayList<>();
            for(int i=0;i<studentCourseDoList.size()-1;i++){
                if(studentCourseDoList.get(i).getStudentid()!= studentCourseDoList.get(i+1).getStudentid()){
                    studentCourseDoList1.add(studentCourseDoList.get(i));
                }
            }
            studentCourseDoList1.add(studentCourseDoList.get(studentCourseDoList.size()-1));
            //System.out.println(studentCourseDoList1);


            for(int i=0;i<studentCourseDoList1.size();i++) {

                CgpaAndSgpaEntity cgpaAndSgpaEntity =new CgpaAndSgpaEntity();

                cgpaAndSgpaEntity.setStudentid(studentRepository.getByRollnumber(studentCourseDoList1.get(i).getStudentid()));
                if (sem == 1) {
                    cgpaAndSgpaEntity.setSgpa1(studentCourseDoList1.get(i).getSgpa());
                }
                if (sem == 2) {
                    cgpaAndSgpaEntity.setSgpa2(studentCourseDoList1.get(i).getSgpa());
                }
                if (sem == 3) {
                    cgpaAndSgpaEntity.setSgpa3(studentCourseDoList1.get(i).getSgpa());
                }
                if (sem == 4) {
                    cgpaAndSgpaEntity.setSgpa4(studentCourseDoList1.get(i).getSgpa());
                }
                if (sem == 5) {
                    cgpaAndSgpaEntity.setSgpa5(studentCourseDoList1.get(i).getSgpa());
                }
                if (sem == 6) {
                    cgpaAndSgpaEntity.setSgpa6(studentCourseDoList1.get(i).getSgpa());
                }
                if (sem == 7) {
                    cgpaAndSgpaEntity.setSgpa7(studentCourseDoList1.get(i).getSgpa());
                }
                if (sem == 8) {
                    cgpaAndSgpaEntity.setSgpa8(studentCourseDoList1.get(i).getSgpa());
                }
                cgpaAndSgpaEntity.setCgpa(studentCourseDoList1.get(i).getCgpa());
                if(!cgpaAndSgpaRepository.existsBystudentid(cgpaAndSgpaEntity.getStudentid())) {
                    cgpaAndSgpaRepository.save(cgpaAndSgpaEntity);
                }
                else
                {
                    CgpaAndSgpaEntity cgpaAndSgpaEntity2=  cgpaAndSgpaRepository.getBystudentid(cgpaAndSgpaEntity.getStudentid());
                    if (sem == 1) {
                        cgpaAndSgpaEntity2.setSgpa1(cgpaAndSgpaEntity.getSgpa1());
                    }
                    if (sem == 2) {
                        cgpaAndSgpaEntity2.setSgpa2(cgpaAndSgpaEntity.getSgpa2());
                    }
                    if (sem == 3) {
                        cgpaAndSgpaEntity2.setSgpa2(cgpaAndSgpaEntity.getSgpa3());
                    }
                    if (sem == 4) {
                        cgpaAndSgpaEntity2.setSgpa4(cgpaAndSgpaEntity.getSgpa4());
                    }
                    if (sem == 5) {
                        cgpaAndSgpaEntity2.setSgpa5(cgpaAndSgpaEntity.getSgpa5());
                    }
                    if (sem == 6) {
                        cgpaAndSgpaEntity2.setSgpa6(cgpaAndSgpaEntity.getSgpa6());
                    }
                    if (sem == 7) {
                        cgpaAndSgpaEntity2.setSgpa7(cgpaAndSgpaEntity.getSgpa7());
                    }
                    if (sem == 8) {
                        cgpaAndSgpaEntity2.setSgpa8(cgpaAndSgpaEntity.getSgpa8());
                    }
                    cgpaAndSgpaEntity2.setCgpa(cgpaAndSgpaEntity.getCgpa());
                    cgpaAndSgpaRepository.save(cgpaAndSgpaEntity2);
                }

            }




        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    public void saveSupply(MultipartFile file, Long sem, String type,String date,String regulation) {

        try {
            List<StudentCourseDO> studentCourseDoList = Helper.excelToDbadvsupAndsup(file.getInputStream(),studentRepository,courseRepository,studentCourseRepository,sem,type, date,regulation);
            // System.out.println(studentCourseEntityList);

            List<StudentCourseEntity> studentCourseEntityList = new ArrayList<>();
            for(int i=0;i<studentCourseDoList.size();i++) {
                studentCourseEntityList.add(studentCourseConvert.convert2StudentCourseEntity(studentCourseDoList.get(i)));
            }
            //check if all the students has student_course entry.
            //List<>attempt1;
            for(int i=0;i< studentCourseEntityList.size();i++){

                StudentEntity studentid= studentRepository.getByRollnumber(studentCourseEntityList.get(i).getStudentid().getRollnumber());
                CourseEntity courseid=courseRepository.getByCourseNameAndCourseRegulation(studentCourseEntityList.get(i).getCourseid().getCourseName(),regulation);
                Long attempt= studentCourseLogRepository.countByStudentidAndCourseid(studentid,courseid);
                StudentCourseLogEntity studentCourseLogEntity=  StudentCourseLogEntity.builder().courseid(courseid).studentid(studentid).attemptdate(date).attemptnumber(attempt+1).grade(studentCourseEntityList.get(i).getGrade()).build();
                studentCourseLogRepository.save(studentCourseLogEntity);

                StudentCourseEntity studentCourseEntity = studentCourseRepository.getByStudentidAndCourseid(studentCourseEntityList.get(i).getStudentid(),studentCourseEntityList.get(i).getCourseid());
                studentCourseEntity.setTotalattempts(attempt+1);
                studentCourseRepository.save(studentCourseEntity);


            }

            for(int i=0; i<studentCourseEntityList.size() ; i++){
                if(!studentCourseEntityList.get(i).getGrade().equals("F")) {
                    StudentCourseEntity studentCourseEntity = studentCourseRepository.getByStudentidAndCourseid(studentCourseEntityList.get(i).getStudentid(), studentCourseEntityList.get(i).getCourseid());
                    studentCourseEntity.setGrade(studentCourseEntityList.get(i).getGrade());
                    studentCourseEntity.setSemester(studentCourseEntityList.get(i).getSemester());
                    studentCourseEntity.setExamdate(studentCourseEntityList.get(i).getExamdate());
                    studentCourseRepository.save(studentCourseEntity);
                }
            }


            //puting cgpa And sgpa
            List<StudentCourseDO> studentCourseDoList1= new ArrayList<>();
            for(int i=0;i<studentCourseDoList.size()-1;i++){
                if(studentCourseDoList.get(i).getStudentid()!= studentCourseDoList.get(i+1).getStudentid()){
                    studentCourseDoList1.add(studentCourseDoList.get(i));
                }
            }
            studentCourseDoList1.add(studentCourseDoList.get(studentCourseDoList.size()-1));
            System.out.println(studentCourseDoList1);

            for(int i=0;i<studentCourseDoList1.size();i++) {

                CgpaAndSgpaEntity cgpaAndSgpaEntity =new CgpaAndSgpaEntity();

                cgpaAndSgpaEntity.setStudentid(studentRepository.getByRollnumber(studentCourseDoList1.get(i).getStudentid()));
                if (sem == 1) {
                    cgpaAndSgpaEntity.setSgpa1(studentCourseDoList1.get(i).getSgpa());
                }
                if (sem == 2) {
                    cgpaAndSgpaEntity.setSgpa2(studentCourseDoList1.get(i).getSgpa());
                }
                if (sem == 3) {
                    cgpaAndSgpaEntity.setSgpa3(studentCourseDoList1.get(i).getSgpa());
                }
                if (sem == 4) {
                    cgpaAndSgpaEntity.setSgpa4(studentCourseDoList1.get(i).getSgpa());
                }
                if (sem == 5) {
                    cgpaAndSgpaEntity.setSgpa5(studentCourseDoList1.get(i).getSgpa());
                }
                if (sem == 6) {
                    cgpaAndSgpaEntity.setSgpa6(studentCourseDoList1.get(i).getSgpa());
                }
                if (sem == 7) {
                    cgpaAndSgpaEntity.setSgpa7(studentCourseDoList1.get(i).getSgpa());
                }
                if (sem == 8) {
                    cgpaAndSgpaEntity.setSgpa8(studentCourseDoList1.get(i).getSgpa());
                }
                cgpaAndSgpaEntity.setCgpa(studentCourseDoList1.get(i).getCgpa());
                if(!cgpaAndSgpaRepository.existsBystudentid(cgpaAndSgpaEntity.getStudentid())) {
                    cgpaAndSgpaRepository.save(cgpaAndSgpaEntity);
                }
                else
                {
                    CgpaAndSgpaEntity cgpaAndSgpaEntity2=  cgpaAndSgpaRepository.getBystudentid(cgpaAndSgpaEntity.getStudentid());
                    if (sem == 1) {
                        cgpaAndSgpaEntity2.setSgpa1(cgpaAndSgpaEntity.getSgpa1());
                    }
                    if (sem == 2) {
                        cgpaAndSgpaEntity2.setSgpa2(cgpaAndSgpaEntity.getSgpa2());
                    }
                    if (sem == 3) {
                        cgpaAndSgpaEntity2.setSgpa2(cgpaAndSgpaEntity.getSgpa3());
                    }
                    if (sem == 4) {
                        cgpaAndSgpaEntity2.setSgpa4(cgpaAndSgpaEntity.getSgpa4());
                    }
                    if (sem == 5) {
                        cgpaAndSgpaEntity2.setSgpa5(cgpaAndSgpaEntity.getSgpa5());
                    }
                    if (sem == 6) {
                        cgpaAndSgpaEntity2.setSgpa6(cgpaAndSgpaEntity.getSgpa6());
                    }
                    if (sem == 7) {
                        cgpaAndSgpaEntity2.setSgpa7(cgpaAndSgpaEntity.getSgpa7());
                    }
                    if (sem == 8) {
                        cgpaAndSgpaEntity2.setSgpa8(cgpaAndSgpaEntity.getSgpa8());
                    }
                    cgpaAndSgpaEntity2.setCgpa(cgpaAndSgpaEntity.getCgpa());
                    cgpaAndSgpaRepository.save(cgpaAndSgpaEntity2);
                }

            }

        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }



        public List<StudentCourseDO>  getAllbybatchid(String Batchid) {
            List<StudentCourseDO> studentCourseDOList =new ArrayList<>();
            List<StudentCourseEntity> studentCourseEntityList = studentCourseRepository.findAll();
            List<StudentCourseEntity> studentCourseEntityList1= new ArrayList<>();
            for(int j=0;j<studentCourseEntityList.size();j++)
            {
                if(studentCourseEntityList.get(j).getStudentid().getBatchid().getBatch().equals(Batchid))
                {
                    studentCourseEntityList1.add(studentCourseEntityList.get(j));
                }
            }

            for(int i=0;i<studentCourseEntityList1.size();i++)
            {
                studentCourseDOList.add(studentCourseConvert.convert2StudentCourseDO(studentCourseEntityList.get(i)));
            }
            return studentCourseDOList;

        }

    public List<StudentCourseDO>  getAllbybatchidandsectionandsemester(String batchid,String section,Long sem) {
        List<StudentCourseDO> studentCourseDOList =new ArrayList<>();
        List<StudentCourseEntity> studentCourseEntityList = studentCourseRepository.findAll();
        List<StudentCourseEntity> studentCourseEntityList1= new ArrayList<>();


        for(int j=0;j<studentCourseEntityList.size();j++)
        {
            //System.out.println(studentCourseEntityList.get(j).getStudentid().getBatchid().getBatch().equals(batchid));
            if(studentCourseEntityList.get(j).getStudentid().getBatchid().getBatch().equals(batchid)&&studentCourseEntityList.get(j).getStudentid().getSection().equals(section)&& studentCourseEntityList.get(j).getSemester()==sem)
            {
                studentCourseEntityList1.add(studentCourseEntityList.get(j)); 
            }
        }

        for(int i=0;i<studentCourseEntityList1.size();i++)
        {
            studentCourseDOList.add(studentCourseConvert.convert2StudentCourseDO(studentCourseEntityList1.get(i)));
            System.out.println(studentCourseDOList.get(i));
        }
        return studentCourseDOList;

    }

    public List<StudentCourseDO>  getAllbybatchidandsemester(String batchid,Long sem) {
        List<StudentCourseDO> studentCourseDOList =new ArrayList<>();
        List<StudentCourseEntity> studentCourseEntityList = studentCourseRepository.findAll();
        List<StudentCourseEntity> studentCourseEntityList1= new ArrayList<>();
        for(int j=0;j<studentCourseEntityList.size();j++)
        {
            if(studentCourseEntityList.get(j).getStudentid().getBatchid().getBatch().equals(batchid) && studentCourseEntityList.get(j).getSemester()==sem)
            {
                studentCourseEntityList1.add(studentCourseEntityList.get(j));
            }
        }

        for(int i=0;i<studentCourseEntityList1.size();i++)
        {
            studentCourseDOList.add(studentCourseConvert.convert2StudentCourseDO(studentCourseEntityList.get(i)));
        }
        return studentCourseDOList;

    }




    public List<StudentCourseDO> getAllbyid(String id) {
        List<StudentCourseDO> studentCourseDOList =new ArrayList<>();
        if(!studentCourseRepository.existsByStudentid(studentRepository.getByRollnumber(id)))
        {
            throw new NoSuchElementException();
        }
        List<StudentCourseEntity> studentCourseEntityList = studentCourseRepository.findAllByStudentid(studentRepository.getByRollnumber(id));

        for(int i=0;i<studentCourseEntityList.size();i++)
        {
            studentCourseDOList.add(studentCourseConvert.convert2StudentCourseDO(studentCourseEntityList.get(i)));
        }
        return studentCourseDOList;

    }

    public List<StudentCourseDO> getAllByIdAndSem(String id,long sem) {
        List<StudentCourseDO> studentCourseDOList =new ArrayList<>();
        if(!studentCourseRepository.existsByStudentidAndSemester(studentRepository.getByRollnumber(id),sem))
        {
            throw new NoSuchElementException();
        }
        List<StudentCourseEntity> studentCourseEntityList = studentCourseRepository.findAllByStudentidAndSemester(studentRepository.getByRollnumber(id),sem);

        for(int i=0;i<studentCourseEntityList.size();i++)
        {
            studentCourseDOList.add(studentCourseConvert.convert2StudentCourseDO(studentCourseEntityList.get(i)));
        }
        return studentCourseDOList;

    }



    public List<LogsDO> getlogs(String studentid, String courseid) {
        List<LogsDO> logsDOList =new ArrayList<>();
        if(!studentCourseLogRepository.existsByStudentidAndCourseid(studentRepository.getByRollnumber(studentid),courseRepository.getByCourseid(courseid)))
        {
            throw new NoSuchElementException();
        }
        List<StudentCourseLogEntity> studentCourseLogEntityList =studentCourseLogRepository.findAllByStudentidAndCourseid(studentRepository.getByRollnumber(studentid),courseRepository.getByCourseid(courseid));

        for(int i=0;i<studentCourseLogEntityList.size();i++)
        {
            logsDOList.add(logsConvert.convert2logsDO(studentCourseLogEntityList.get(i)));
        }
        return logsDOList;

    }

    public void updateStudentsBatch(List<StudentDO> studentDOList,String batch){
            List<StudentEntity> studentEntityList=new ArrayList<>();
            for(int h=0; h<studentDOList.size() ; h++){
                studentEntityList.add(studentRepository.getByRollnumber(studentDOList.get(h).getRollnumber()));
                studentEntityList.get(h).setBatchid(batchRepository.getByBatch(batch));
            }
            studentRepository.saveAll(studentEntityList);
    }
        public void updateStudentCourse(StudentCourseDO studentCourseDO){

            if(studentCourseDO.getStudentid()==null||studentCourseDO.getStudentid().isEmpty()||studentCourseDO.getCourseid()==null||studentCourseDO.getCourseid().isEmpty())
            {
                throw new EmptyFieldException();
            }
            StudentEntity studentEntity= studentRepository.getByRollnumber(studentCourseDO.getStudentid());
            CourseEntity courseEntity= courseRepository.getByCourseid(studentCourseDO.getCourseid());

            if(!studentCourseRepository.existsByStudentidAndCourseid(studentEntity,courseEntity)){
                throw new NoSuchElementException();
            }
               StudentCourseEntity studentCourseEntity =  studentCourseRepository.findByStudentidAndCourseid(studentEntity,courseEntity);

            //System.out.println(studentCourseEntity);
            if(studentCourseDO.getGrade()!=null){
                studentCourseEntity.setGrade(studentCourseDO.getGrade());
                studentCourseRepository.save(studentCourseEntity);
            }
            if(studentCourseDO.getSemester()!=0){
                studentCourseEntity.setSemester(studentCourseDO.getSemester());
                studentCourseRepository.save(studentCourseEntity);
            }
            if(studentCourseDO.getGrade()==null && studentCourseDO.getSemester()==0){
                throw new NoDataSentException();
            }
        }
    public  List<StudentCourseDO> getStudentsByBatchAndCourse(String batch, String courseName)
    {
        //fetch all the students of given batch
        List<StudentEntity> studentEntities = studentRepository.getAllByBatchid(batchRepository.getByBatch(batch));
        //fetch course entity of given course
        CourseEntity courseEntity= courseRepository.getByCourseNameAndCourseRegulation(courseName,batchRepository.getByBatch(batch).getRegulation());

        //fetch students with the given course entries.
        List<StudentCourseEntity> studentCourseEntityList = new ArrayList<>();
        for(int i=0;i<studentEntities.size();i++)
        {
            if( studentCourseRepository.existsByStudentidAndCourseid(studentEntities.get(i),courseEntity) ){
                studentCourseEntityList.add(studentCourseRepository.getByStudentidAndCourseid(studentEntities.get(i),courseEntity));
            }
        }
        //'O','A+','A','B+','B','C','P','F','I'
        List<StudentCourseDO> studentCourseOgradeDOList = new ArrayList<>();
        List<StudentCourseDO> studentCourseAplusgradeDOList = new ArrayList<>();
        List<StudentCourseDO> studentCourseAgradeDOList = new ArrayList<>();
        List<StudentCourseDO> studentCourseBplusgradeDOList = new ArrayList<>();
        List<StudentCourseDO> studentCourseBgradeDOList = new ArrayList<>();
        List<StudentCourseDO> studentCourseCgradeDOList = new ArrayList<>();
        List<StudentCourseDO> studentCoursePgradeDOList = new ArrayList<>();
        List<StudentCourseDO> studentCourseFgradeDOList = new ArrayList<>();

        for(int h = 0; h < studentCourseEntityList.size() ; h++){
            if( studentCourseEntityList.get(h).getGrade().equals("O") ){
                studentCourseOgradeDOList.add(studentCourseConvert.convert2StudentCourseDO(studentCourseEntityList.get(h)));
            }else if(studentCourseEntityList.get(h).getGrade().equals("A+")){
                studentCourseAplusgradeDOList.add(studentCourseConvert.convert2StudentCourseDO(studentCourseEntityList.get(h)));
            }else if(studentCourseEntityList.get(h).getGrade().equals("A")){
                studentCourseAgradeDOList.add(studentCourseConvert.convert2StudentCourseDO(studentCourseEntityList.get(h)));
            }else if(studentCourseEntityList.get(h).getGrade().equals("B+")){
                studentCourseBplusgradeDOList.add(studentCourseConvert.convert2StudentCourseDO(studentCourseEntityList.get(h)));
            }else if(studentCourseEntityList.get(h).getGrade().equals("B")){
                studentCourseBgradeDOList.add(studentCourseConvert.convert2StudentCourseDO(studentCourseEntityList.get(h)));
            }else if(studentCourseEntityList.get(h).getGrade().equals("C")){
                studentCourseCgradeDOList.add(studentCourseConvert.convert2StudentCourseDO(studentCourseEntityList.get(h)));
            }else if(studentCourseEntityList.get(h).getGrade().equals("P")){
                studentCoursePgradeDOList.add(studentCourseConvert.convert2StudentCourseDO(studentCourseEntityList.get(h)));
            }else if(studentCourseEntityList.get(h).getGrade().equals("F")){
                studentCourseFgradeDOList.add(studentCourseConvert.convert2StudentCourseDO(studentCourseEntityList.get(h)));
            }
        }
        List<StudentCourseDO> studentCourseDOList = new ArrayList<>();
        studentCourseDOList.addAll(studentCourseOgradeDOList);
        studentCourseDOList.addAll(studentCourseAplusgradeDOList);
        studentCourseDOList.addAll(studentCourseAgradeDOList);
        studentCourseDOList.addAll(studentCourseBplusgradeDOList);
        studentCourseDOList.addAll(studentCourseBgradeDOList);
        studentCourseDOList.addAll(studentCourseCgradeDOList);
        studentCourseDOList.addAll(studentCoursePgradeDOList);
        studentCourseDOList.addAll(studentCourseFgradeDOList);
        return studentCourseDOList;
    }
    public  List<StudentCourseDO> getFailedStudentsByBatchAndCourse(String batch, String courseName)
    {
        //fetch all the students of given batch
        List<StudentEntity> studentEntities = studentRepository.getAllByBatchid(batchRepository.getByBatch(batch));
        //fetch course entity of given course
        CourseEntity courseEntity= courseRepository.getByCourseNameAndCourseRegulation(courseName,batchRepository.getByBatch(batch).getRegulation());

        //fetch students with the given course entries.
        List<StudentCourseEntity> studentCourseEntityList = new ArrayList<>();
        for(int i=0;i<studentEntities.size();i++)
        {
            if( studentCourseRepository.existsByStudentidAndCourseid(studentEntities.get(i),courseEntity) ){
                studentCourseEntityList.add(studentCourseRepository.getByStudentidAndCourseid(studentEntities.get(i),courseEntity));
            }
        }
        //'O','A+','A','B+','B','C','P','F','I'

        List<StudentCourseDO> studentCourseFgradeDOList = new ArrayList<>();

        for(int h = 0; h < studentCourseEntityList.size() ; h++){
            if(studentCourseEntityList.get(h).getGrade().equals("F")){
                studentCourseFgradeDOList.add(studentCourseConvert.convert2StudentCourseDO(studentCourseEntityList.get(h)));
            }
        }

        return studentCourseFgradeDOList;
    }
    public  List<StudentBacklogDO>  getstudentswithNmaxBacklogs(String batch,int N)
    {
        //fetch all the students of given batch
        List<StudentEntity> studentEntities = studentRepository.getAllByBatchid(batchRepository.getByBatch(batch));


        //List<StudentCourseDO> studentCourseDOList = new ArrayList<>();

        List<StudentCourseEntity> studentCourseEntityList = new ArrayList<>();


        List<StudentBacklogDO> studentBacklogDOList =  new ArrayList<>();
        for(int i=0;i<studentEntities.size();i++)
        {
            StudentBacklogDO studentBacklogDO = new StudentBacklogDO();
            studentCourseEntityList = studentCourseRepository.getAllByStudentidAndGrade(studentEntities.get(i),"F");
            if(studentCourseEntityList.size()<=N){
                studentBacklogDO.setStudentid(studentEntities.get(i).getRollnumber());
                List<StudentCourseDO> studentCourseDOList = new ArrayList<>();
                for(int h = 0; h < studentCourseEntityList.size(); h++){
                        studentCourseDOList.add(studentCourseConvert.convert2StudentCourseDO(studentCourseEntityList.get(h)));
                }
                studentBacklogDO.setStudentCourseDOList(studentCourseDOList);
                studentBacklogDOList.add(studentBacklogDO);
            }
            studentCourseEntityList.clear();
            //if( studentCourseRepository.existsByStudentidAndCourseid(studentEntities.get(i),courseEntity) ){
            //    studentCourseEntityList.add(studentCourseRepository.getByStudentidAndCourseid(studentEntities.get(i),courseEntity));
            //}
        }
        return studentBacklogDOList;
    }

    public  List<CgpaAndSgpaDO>  getstudentsListShortlistByCGPA(String batch,Float cgpa)
    {
        //fetch all the students of given batch
        List<StudentEntity> studentEntities = studentRepository.getAllByBatchid(batchRepository.getByBatch(batch));

        List<CgpaAndSgpaDO> cgpaAndSgpaDOList =  new ArrayList<>();

        for(int h=0; h<studentEntities.size() ; h++){
            CgpaAndSgpaDO cgpaAndSgpaDO = new CgpaAndSgpaDO();
            //System.out.println(cgpa);
            if(!cgpaAndSgpaRepository.getBystudentid( studentEntities.get(h) ).getCgpa().equals("") && Float.parseFloat( cgpaAndSgpaRepository.getBystudentid( studentEntities.get(h) ).getCgpa() ) >= cgpa ) {
                CgpaAndSgpaEntity cgpaAndSgpaEntity = cgpaAndSgpaRepository.getBystudentid(studentEntities.get(h));
                cgpaAndSgpaDO =  cgpaAndSgpaConver.convert2CgpaAndSgpaDO(cgpaAndSgpaEntity);
                cgpaAndSgpaDOList.add(cgpaAndSgpaDO);
            }
        }
        sort(cgpaAndSgpaDOList);
        return cgpaAndSgpaDOList;
    }

    public  List<CgpaAndSgpaDO>  getstudentsListSortByCGPA(String batch)
    {
        //fetch all the students of given batch
        List<StudentEntity> studentEntities = studentRepository.getAllByBatchid(batchRepository.getByBatch(batch));

        //List<StudentDO> studentDOList =  new ArrayList<>();
        List<CgpaAndSgpaDO> cgpaAndSgpaDOList =  new ArrayList<>();


        for(int h=0; h<studentEntities.size() ; h++){
            CgpaAndSgpaDO cgpaAndSgpaDO = new CgpaAndSgpaDO();
            //System.out.println(cgpa);
            CgpaAndSgpaEntity cgpaAndSgpaEntity = cgpaAndSgpaRepository.getBystudentid(studentEntities.get(h));
            cgpaAndSgpaDO =  cgpaAndSgpaConver.convert2CgpaAndSgpaDO(cgpaAndSgpaEntity);
            cgpaAndSgpaDOList.add(cgpaAndSgpaDO);
        }
        sort(cgpaAndSgpaDOList);
        return cgpaAndSgpaDOList;
    }
    public static void sort(List<CgpaAndSgpaDO> list)
    {
        list.sort(Comparator.comparing(CgpaAndSgpaDO::getCgpa,Comparator.reverseOrder()));
    }

    public  List<CgpaAndSgpaDO>  getstudentsListSortBySGPA(String batch,String sem)
    {
        //fetch all the students of given batch
        List<StudentEntity> studentEntities = studentRepository.getAllByBatchid(batchRepository.getByBatch(batch));

        List<CgpaAndSgpaDO> cgpaAndSgpaDOList =  new ArrayList<>();

        for(int h=0; h<studentEntities.size() ; h++){
            CgpaAndSgpaDO cgpaAndSgpaDO = new CgpaAndSgpaDO();
            //System.out.println(cgpa);
            CgpaAndSgpaEntity cgpaAndSgpaEntity = cgpaAndSgpaRepository.getBystudentid(studentEntities.get(h));
            cgpaAndSgpaDO =  cgpaAndSgpaConver.convert2CgpaAndSgpaDO(cgpaAndSgpaEntity);
            cgpaAndSgpaDOList.add(cgpaAndSgpaDO);
        }

        return cgpaAndSgpaDOList;
    }
}
