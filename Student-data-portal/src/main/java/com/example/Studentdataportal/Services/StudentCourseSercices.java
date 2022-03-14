package com.example.Studentdataportal.Services;

import com.example.Studentdataportal.DataObjects.LogsDo;
import com.example.Studentdataportal.DataObjects.StudentCourseDO;
import com.example.Studentdataportal.Entitis.CourseEntity;
import com.example.Studentdataportal.Entitis.StudentCourseEntity;
import com.example.Studentdataportal.Entitis.StudentCourseLogEntity;
import com.example.Studentdataportal.Entitis.StudentEntity;
import com.example.Studentdataportal.Repositorys.CourseRepository;
import com.example.Studentdataportal.Repositorys.StudentCourseLogRepository;
import com.example.Studentdataportal.Repositorys.StudentCourseRepository;
import com.example.Studentdataportal.Repositorys.StudentRepository;
import com.example.Studentdataportal.Util.Helper;
import com.example.Studentdataportal.Util.StudentCourseConvert;
import com.example.Studentdataportal.Util.LogsConvert;
import com.example.Studentdataportal.exception.EmptyFieldException;
import com.example.Studentdataportal.exception.MultipleAttemptsPresentException;
import com.example.Studentdataportal.exception.NoDataSentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StudentCourseSercices {
        @Autowired
        StudentCourseRepository studentCourseRepository;
        @Autowired
        StudentCourseConvert studentCourseConvert;
        @Autowired
        StudentRepository studentRepository;
        @Autowired
        CourseRepository courseRepository;

        @Autowired
         StudentCourseLogRepository studentCourseLogRepository;
        @Autowired
        LogsConvert logsConvert;


        public void save(MultipartFile file, Long sem, String type,String date,String regulation) {

            try {
                List<StudentCourseEntity> studentCourseEntityList = Helper.excelToDb(file.getInputStream(),studentRepository,courseRepository,studentCourseRepository,sem,type, date,regulation);
              // System.out.println(studentCourseEntityList);
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
            } catch (IOException e) {
                throw new RuntimeException("fail to store excel data: " + e.getMessage());
            }
        }

    @Transactional
    public void saveadvsup(MultipartFile file, Long sem, String type,String data,String regulation) {
        try {
            List<StudentCourseEntity> studentCourseEntityList = Helper.excelToDbadvsupAndsup(file.getInputStream(),studentRepository,courseRepository,studentCourseRepository,sem,type, data,regulation);
            //  System.out.println(studentCourseEntityList);

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
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    public void saveSupply(MultipartFile file, Long sem, String type,String date,String regulation) {

        try {
            List<StudentCourseEntity> studentCourseEntityList = Helper.excelToDbadvsupAndsup(file.getInputStream(),studentRepository,courseRepository,studentCourseRepository,sem,type, date,regulation);
            // System.out.println(studentCourseEntityList);

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

    public List<StudentCourseDO>  getAllbybatchidAndSection(String batchid,String section) {
        List<StudentCourseDO> studentCourseDOList =new ArrayList<>();
        List<StudentCourseEntity> studentCourseEntityList = studentCourseRepository.findAll();
        List<StudentCourseEntity> studentCourseEntityList1= new ArrayList<>();
        for(int j=0;j<studentCourseEntityList.size();j++)
        {
            if(studentCourseEntityList.get(j).getStudentid().getBatchid().getBatch().equals(batchid)&&studentCourseEntityList.get(j).getStudentid().getSection().equals(section))
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



    public List<LogsDo> getlogs(String studentid,String courseid) {
        List<LogsDo> logsDoList =new ArrayList<>();
        if(!studentCourseLogRepository.existsByStudentidAndCourseid(studentRepository.getByRollnumber(studentid),courseRepository.getByCourseid(courseid)))
        {
            throw new NoSuchElementException();
        }
        List<StudentCourseLogEntity> studentCourseLogEntityList =studentCourseLogRepository.findAllByStudentidAndCourseid(studentRepository.getByRollnumber(studentid),courseRepository.getByCourseid(courseid));

        for(int i=0;i<studentCourseLogEntityList.size();i++)
        {
            logsDoList.add(logsConvert.convert2logsDO(studentCourseLogEntityList.get(i)));
        }
        return logsDoList;

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

}
