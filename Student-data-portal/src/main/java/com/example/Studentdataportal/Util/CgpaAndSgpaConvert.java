package com.example.Studentdataportal.Util;

import com.example.Studentdataportal.DataObjects.BatchDO;
import com.example.Studentdataportal.DataObjects.CgpaAndSgpaDO;
import com.example.Studentdataportal.Entitis.BatchEntity;
import com.example.Studentdataportal.Entitis.CgpaAndSgpaEntity;
import org.springframework.stereotype.Component;

@Component
public class CgpaAndSgpaConvert {
    public  CgpaAndSgpaDO convert2CgpaAndSgpaDO(CgpaAndSgpaEntity cgpaAndSgpaEntity)
    {
        return CgpaAndSgpaDO.builder().studentid(cgpaAndSgpaEntity.getStudentid()).cgpa(cgpaAndSgpaEntity.getCgpa()).
                sgpa1(cgpaAndSgpaEntity.getSgpa1()).sgpa2(cgpaAndSgpaEntity.getSgpa2()).sgpa3(cgpaAndSgpaEntity.getSgpa3()).sgpa4(cgpaAndSgpaEntity.getSgpa4()).sgpa5(cgpaAndSgpaEntity.getSgpa5()).sgpa6(cgpaAndSgpaEntity.getSgpa6()).sgpa7(cgpaAndSgpaEntity.getSgpa7()).sgpa8(cgpaAndSgpaEntity.getSgpa8()).build();
    }
}
