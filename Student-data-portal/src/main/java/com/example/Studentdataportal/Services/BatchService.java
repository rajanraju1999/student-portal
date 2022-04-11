package com.example.Studentdataportal.Services;


import com.example.Studentdataportal.DataObjects.BatchDO;
import com.example.Studentdataportal.DataObjects.BatchReportDO;
import com.example.Studentdataportal.DataObjects.CgpaAndSgpaDO;
import com.example.Studentdataportal.Entitis.BatchEntity;
import com.example.Studentdataportal.Entitis.CgpaAndSgpaEntity;
import com.example.Studentdataportal.Entitis.StudentEntity;
import com.example.Studentdataportal.Repositorys.BatchRepository;
import com.example.Studentdataportal.Repositorys.CgpaAndSgpaRepository;
import com.example.Studentdataportal.Repositorys.StudentRepository;
import com.example.Studentdataportal.Util.CgpaAndSgpaConvert;
import com.example.Studentdataportal.Util.BatchConvert;
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
    BatchConvert batchConvert;

    @Autowired
    CgpaAndSgpaRepository cgpaAndSgpaRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CgpaAndSgpaConvert cgpaAndSgpaConvert;
    @Autowired
    StudentCourseServices studentCourseServices;
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

           if((cgpaAndSgpaEntity.getSgpa1() == null || cgpaAndSgpaEntity.getSgpa1().isEmpty()) || (cgpaAndSgpaEntity.getSgpa2()==null||cgpaAndSgpaEntity.getSgpa2().isEmpty()) || (cgpaAndSgpaEntity.getSgpa3()==null||cgpaAndSgpaEntity.getSgpa3().isEmpty()) || (cgpaAndSgpaEntity.getSgpa4()==null||cgpaAndSgpaEntity.getSgpa4().isEmpty()) || (cgpaAndSgpaEntity.getSgpa5()==null||cgpaAndSgpaEntity.getSgpa5().isEmpty()) || (cgpaAndSgpaEntity.getSgpa6()==null||cgpaAndSgpaEntity.getSgpa6().isEmpty()) || (cgpaAndSgpaEntity.getSgpa7()==null||cgpaAndSgpaEntity.getSgpa7().isEmpty()) || (cgpaAndSgpaEntity.getSgpa8()==null||cgpaAndSgpaEntity.getSgpa8().isEmpty()) || (cgpaAndSgpaEntity.getCgpa()==null||cgpaAndSgpaEntity.getCgpa().isEmpty())){



           }
           else {

               cgpaAndSgpaEntityList.add(cgpaAndSgpaEntity);
           }
       }
       if(cgpaAndSgpaEntityList.isEmpty()){
           throw new BatchReportException(batchid);
       }

       for(int i=0;i<cgpaAndSgpaEntityList.size();i++)
       {

           cgpaAndSgpaDOList.add(cgpaAndSgpaConvert.convert2CgpaAndSgpaDO(cgpaAndSgpaEntityList.get(i)));
       }


        BatchReportDO batchReportDO = new  BatchReportDO();


        batchReportDO.setGraduationlist(cgpaAndSgpaDOList);
        batchReportDO.setBatchpasspercentage((double) cgpaAndSgpaEntityList.size()/studentEntityList.size()*100);
        batchReportDO.setBatchtoper(studentCourseServices.getstudentsListSortByCGPA(batchid).get(0));

        return batchReportDO;
    }
}
