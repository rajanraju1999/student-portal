package com.example.Studentdataportal.Services;

import com.example.Studentdataportal.DataObjects.GeneralReportDO;
import com.example.Studentdataportal.Entitis.CourseEntity;
import com.example.Studentdataportal.Entitis.StudentCourseEntity;
import com.example.Studentdataportal.Entitis.StudentEntity;
import com.example.Studentdataportal.Repositorys.BatchRepository;
import com.example.Studentdataportal.Repositorys.CourseRepository;
import com.example.Studentdataportal.Repositorys.StudentCourseRepository;
import com.example.Studentdataportal.Repositorys.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GeneralReportsServices {
    @Autowired
    BatchRepository batchRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    StudentCourseRepository studentCourseRepository;
    public List<GeneralReportDO> getgeneralreportoverall(String batchid,Long sem){

        List<StudentEntity> studentEntityList =studentRepository.getAllByBatchid(batchRepository.getByBatch(batchid));
        List<StudentCourseEntity> studentCourseEntityList=new ArrayList<>();
        List<GeneralReportDO> generalReportDOList =new ArrayList<>();
          for(int i=0;i<studentEntityList.size();i++)
          {

              studentCourseEntityList=studentCourseRepository.getAllByStudentidAndSemester(studentEntityList.get(i),sem);
          }

        Set<CourseEntity> courseEntitySet=new HashSet<>();
        List<CourseEntity> courseEntityList=new ArrayList<>();




        List<StudentCourseEntity> studentCourseEntityList1 =new ArrayList<>();
        for(int i=0;i<studentCourseEntityList.size();i++)
          courseEntitySet.add(studentCourseEntityList.get(i).getCourseid());

        //System.out.println(courseEntitySet);
        courseEntityList.addAll(courseEntitySet);
       int pass = 0;
       int Ograde=0;
       int Aplusgrade=0;
       int Agrade=0;
   for(int i=0;i<courseEntityList.size();i++) {

       studentCourseEntityList1=studentCourseRepository.getAllByCourseidAndSemester(courseEntityList.get(i),sem);
      // System.out.println(studentCourseEntityList1);
      for(int j=0;j<studentCourseEntityList1.size();j++) {
          if (!studentCourseEntityList1.get(j).getGrade().equals("F")) {
              pass++;
          }
          if (studentCourseEntityList1.get(j).getGrade().equals("O") ) {
              Ograde++;
          }
          if (studentCourseEntityList1.get(j).getGrade().equals("A+") ) {
              Aplusgrade++;
          }
          if (studentCourseEntityList1.get(j).getGrade().equals("A")) {
              Agrade++;
          }
      }

       generalReportDOList.add(GeneralReportDO.builder().coursename(courseEntityList.get(i).getCourseName()).noofstudentsappeared(studentCourseEntityList1.size()).noofstudentspassed(pass).passpercentage((int)(((float)pass/studentCourseEntityList1.size())*100)).Ogreadscount(Ograde).Aplusgreadscount(Aplusgrade).Agreadscount(Agrade).build());

       studentCourseEntityList1.clear();
        pass = 0;
        Ograde=0;
        Aplusgrade=0;
        Agrade=0;

   }

        int fail=0;
        Set<StudentEntity> studentEntitySet =new HashSet<>();
        List<StudentCourseEntity> studentCourseEntityList4= new ArrayList<>();

        int passcount=0;
        for(int i=0;i<studentEntityList.size();i++) {
            studentCourseEntityList4 = studentCourseRepository.getAllByStudentidAndSemester(studentEntityList.get(i), sem);

            int flag=0;
            for (int j = 0; j < studentCourseEntityList4.size(); j++) {
                if (studentCourseEntityList4.get(j).getGrade().equals("F")) {


                    flag=1;
                    break;
                }
            }
            if(flag==0)
            {
                passcount++;
            }
            studentCourseEntityList4.clear();
        }
        System.out.println(studentCourseEntityList4);
        generalReportDOList.add(GeneralReportDO.builder().noofstudentsappeared(studentEntityList.size()).noofstudentspassed(passcount).overalpasspercentage(  (int)  (( (float) passcount/studentEntityList.size() )*100)).build());
        return generalReportDOList;

    }

    public List<GeneralReportDO> getgeneralreport(String batchid,Long sem,String Section){

        List<StudentEntity> studentEntityList =studentRepository.getAllByBatchid(batchRepository.getByBatch(batchid));
        System.out.println(studentEntityList);
        List<StudentCourseEntity> studentCourseEntityList=new ArrayList<>();
        List<GeneralReportDO> generalReportDOList =new ArrayList<>();
        for(int i=0;i<studentEntityList.size();i++)
        {

            studentCourseEntityList=studentCourseRepository.getAllByStudentidAndSemester(studentEntityList.get(i),sem);
        }

        Set<CourseEntity> courseEntitySet=new HashSet<>();
        List<CourseEntity> courseEntityList=new ArrayList<>();




        List<StudentCourseEntity> studentCourseEntityList1 =new ArrayList<>();
        List<StudentCourseEntity> studentCourseEntityList2 =new ArrayList<>();
        for(int i=0;i<studentCourseEntityList.size();i++)
            courseEntitySet.add(studentCourseEntityList.get(i).getCourseid());

        //System.out.println(courseEntitySet);
        courseEntityList.addAll(courseEntitySet);
        int pass = 0;
        int Ograde=0;
        int Aplusgrade=0;
        int Agrade=0;
        for(int i=0;i<courseEntityList.size();i++) {

            studentCourseEntityList1=studentCourseRepository.getAllByCourseidAndSemester(courseEntityList.get(i),sem);
             //System.out.println(studentCourseEntityList1);
            for(int k=0;k<studentCourseEntityList1.size();k++)
            {
                if(studentCourseEntityList1.get(k).getStudentid().getSection().equals(Section)){
                    studentCourseEntityList2.add(studentCourseEntityList1.get(k));
                }
            }

         //   System.out.println(studentCourseEntityList2);
            for(int j=0;j<studentCourseEntityList2.size();j++) {
                if (!studentCourseEntityList2.get(j).getGrade().equals("F")) {
                    pass++;

                }
                if (studentCourseEntityList2.get(j).getGrade().equals("O") ) {
                    Ograde++;

                }
                if (studentCourseEntityList2.get(j).getGrade().equals("A+") ) {
                    Aplusgrade++;

                }
                if (studentCourseEntityList2.get(j).getGrade().equals("A")) {
                    Agrade++;

                }

            }

            generalReportDOList.add(GeneralReportDO.builder().coursename(courseEntityList.get(i).getCourseName()).noofstudentsappeared(studentCourseEntityList2.size()).noofstudentspassed(pass).passpercentage((int)(((float)pass/studentCourseEntityList2.size())*100)).Ogreadscount(Ograde).Aplusgreadscount(Aplusgrade).Agreadscount(Agrade).build());

            studentCourseEntityList1.clear();
            studentCourseEntityList2.clear();
            pass = 0;
            Ograde=0;
            Aplusgrade=0;
            Agrade=0;

        }
        int fail=0;
        Set<StudentEntity> studentEntitySet =new HashSet<>();
        List<StudentCourseEntity> studentCourseEntityList4= new ArrayList<>();

        int passcount=0;
       for(int i=0;i<studentEntityList.size();i++) {
           studentCourseEntityList4 = studentCourseRepository.getAllByStudentidAndSemester(studentEntityList.get(i), sem);

           int flag=0;
           for (int j = 0; j < studentCourseEntityList4.size(); j++) {
               if (studentCourseEntityList4.get(j).getGrade().equals("F")) {


                  flag=1;
                  break;
               }
           }
           if(flag==0)
           {
               passcount++;
           }
           studentCourseEntityList4.clear();
       }
        System.out.println(studentCourseEntityList4);
        generalReportDOList.add(GeneralReportDO.builder().noofstudentsappeared(studentEntityList.size()).noofstudentspassed(passcount).overalpasspercentage(  (int)  (( (float) passcount/studentEntityList.size() )*100)).build());
        return generalReportDOList;

    }




}
