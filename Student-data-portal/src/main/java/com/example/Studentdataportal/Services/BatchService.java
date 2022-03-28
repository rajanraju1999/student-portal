package com.example.Studentdataportal.Services;


import com.example.Studentdataportal.DataObjects.BatchDO;
import com.example.Studentdataportal.DataObjects.CourseDO;
import com.example.Studentdataportal.Entitis.BatchEntity;
import com.example.Studentdataportal.Entitis.CourseEntity;
import com.example.Studentdataportal.Repositorys.BatchRepository;
import com.example.Studentdataportal.Util.batchConvert;
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
}
