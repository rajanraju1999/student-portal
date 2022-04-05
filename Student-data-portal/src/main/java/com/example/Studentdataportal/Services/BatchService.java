package com.example.Studentdataportal.Services;


import com.example.Studentdataportal.DataObjects.BatchDO;
import com.example.Studentdataportal.DataObjects.BatchReportDO;
import com.example.Studentdataportal.DataObjects.CgpaAndSgpaDO;
import com.example.Studentdataportal.DataObjects.CourseDO;
import com.example.Studentdataportal.Entitis.BatchEntity;
import com.example.Studentdataportal.Entitis.CgpaAndSgpaEntity;
import com.example.Studentdataportal.Entitis.CourseEntity;
import com.example.Studentdataportal.Entitis.StudentEntity;
import com.example.Studentdataportal.Repositorys.BatchRepository;
import com.example.Studentdataportal.Repositorys.CgpaAndSgpaRepository;
import com.example.Studentdataportal.Repositorys.StudentRepository;
import com.example.Studentdataportal.Util.CgpaAndSgpaConvert;
import com.example.Studentdataportal.Util.batchConvert;
import com.example.Studentdataportal.exception.BatchReportException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BatchService {
    @Autowired
    BatchRepository batchRepository;
    @Autowired
    batchConvert batchConvert;

    @Autowired
    CgpaAndSgpaRepository cgpaAndSgpaRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CgpaAndSgpaConvert cgpaAndSgpaConvert;
    public List<BatchDO> getallbatches()
    {
        List<BatchEntity> batchList = batchRepository.findAll();
        List<BatchDO> batchDOsList =new ArrayList<>();
        for(int i=0;i<batchList.size();i++)
        {
            batchDOsList.add(batchConvert.convert2BatchDO(batchList.get(i)));
        }
        return batchDOsList;
    }

    public BatchReportDO getbatchreport(String batchid){




        List<StudentEntity> studentEntityList =studentRepository.getAllByBatchid(batchRepository.getByBatch(batchid));
        List<CgpaAndSgpaEntity> cgpaAndSgpaEntityList = new ArrayList<>();
        List<CgpaAndSgpaDO> cgpaAndSgpaDOList = new ArrayList<>();
       for(int i=0;i<studentEntityList.size();i++) {



           CgpaAndSgpaEntity cgpaAndSgpaEntity = cgpaAndSgpaRepository.getBystudentid(studentEntityList.get(i));

           if((cgpaAndSgpaEntity.getSgpa1() == null || cgpaAndSgpaEntity.getSgpa1().isEmpty()) && (cgpaAndSgpaEntity.getSgpa2()==null||cgpaAndSgpaEntity.getSgpa2().isEmpty()) && (cgpaAndSgpaEntity.getSgpa3()==null||cgpaAndSgpaEntity.getSgpa3().isEmpty()) && (cgpaAndSgpaEntity.getSgpa4()==null||cgpaAndSgpaEntity.getSgpa4().isEmpty()) && (cgpaAndSgpaEntity.getSgpa5()==null||cgpaAndSgpaEntity.getSgpa5().isEmpty()) && (cgpaAndSgpaEntity.getSgpa6()==null||cgpaAndSgpaEntity.getSgpa6().isEmpty()) && (cgpaAndSgpaEntity.getSgpa7()==null||cgpaAndSgpaEntity.getSgpa7().isEmpty()) && (cgpaAndSgpaEntity.getSgpa8()==null||cgpaAndSgpaEntity.getSgpa8().isEmpty()) && (cgpaAndSgpaEntity.getCgpa()==null||cgpaAndSgpaEntity.getCgpa().isEmpty())){

               throw new BatchReportException(batchid);

           }
           else {

               cgpaAndSgpaEntityList.add(cgpaAndSgpaEntity);
           }
       }
        CgpaAndSgpaEntity cgpaAndSgpaEntity1 =cgpaAndSgpaEntityList.get(1);
       for(int i=0;i<cgpaAndSgpaEntityList.size();i++)
       {

           cgpaAndSgpaDOList.add(cgpaAndSgpaConvert.convert2CgpaAndSgpaDO(cgpaAndSgpaEntityList.get(i)));
           if(Integer.parseInt(cgpaAndSgpaEntity1.getCgpa())<Integer.parseInt(cgpaAndSgpaEntityList.get(i).getCgpa()))
           {
               cgpaAndSgpaEntity1.setCgpa(cgpaAndSgpaEntityList.get(i).getCgpa());
           }
       }
        CgpaAndSgpaDO cgpaAndSgpaDO= cgpaAndSgpaConvert.convert2CgpaAndSgpaDO(cgpaAndSgpaEntity1);

        BatchReportDO batchReportDO = new  BatchReportDO();


        batchReportDO.setGraduationlist(cgpaAndSgpaDOList);
        batchReportDO.setBatchpasspercentage((double) cgpaAndSgpaEntityList.size()/studentEntityList.size());
        batchReportDO.setBatchtoper(cgpaAndSgpaDO);

        return batchReportDO;
    }
}
