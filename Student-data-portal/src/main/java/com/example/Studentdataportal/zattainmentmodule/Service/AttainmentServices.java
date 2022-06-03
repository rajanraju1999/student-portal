package com.example.Studentdataportal.zattainmentmodule.Service;

import com.example.Studentdataportal.DataObjects.CourseDO;
import com.example.Studentdataportal.DataObjects.StudentDO;
import com.example.Studentdataportal.Entitis.*;
import com.example.Studentdataportal.Repositorys.BatchRepository;
import com.example.Studentdataportal.Repositorys.CourseRepository;
import com.example.Studentdataportal.Util.CourseConvert;
import com.example.Studentdataportal.Util.Helper;
import com.example.Studentdataportal.exception.*;
import com.example.Studentdataportal.zattainmentmodule.DataObjects.AssignDO;
import com.example.Studentdataportal.zattainmentmodule.DataObjects.AttainmentDO;
import com.example.Studentdataportal.zattainmentmodule.DataObjects.AttainmentReportDO;
import com.example.Studentdataportal.zattainmentmodule.Entity.AssignEntity;
import com.example.Studentdataportal.zattainmentmodule.Entity.AttainmentEntity;
import com.example.Studentdataportal.zattainmentmodule.Repository.AssignRepository;
import com.example.Studentdataportal.zattainmentmodule.Repository.AttainmentRepository;
import com.example.Studentdataportal.zattainmentmodule.Util.AssignConvert;
import com.example.Studentdataportal.zattainmentmodule.Util.AttainmentConvert;
import com.example.Studentdataportal.zattainmentmodule.Util.NewHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class AttainmentServices {
    @Autowired
    BatchRepository batchRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    AttainmentConvert attainmentConvert;
    @Autowired
    CourseConvert courseConvert;
    @Autowired
    AttainmentRepository attainmentRepository;
    @Autowired
    AssignConvert assignConvert;
    @Autowired
    AssignRepository assignRepository;

    public void save(MultipartFile file, String batch, String courseName,String regulation) {

        try {

            if(!batchRepository.existsByBatch(batch)){
                throw new BatchDoesNotExistException(batch);
            }

            if(!courseRepository.existsByCourseNameAndCourseRegulation(courseName,regulation)){
                throw new NoCourseNameAndRegulationException(courseName,regulation);
            }

            if(attainmentRepository.existsByBatchidAndCourseid(batchRepository.getByBatch(batch),courseRepository.getByCourseName(courseName))){
                throw new AlreadyCourseNameExistForGivenBatchException(batch,courseName);
            }

            AssignEntity assignEntity = assignRepository.getByBatchidAndCourseid(batchRepository.getByBatch(batch),courseRepository.getByCourseName(courseName));

            //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            //System.out.println(formatter.format(date));

            if(!assignEntity.getDate().after(date)){
                throw new DeadlineErrorException();
            }


            List<AttainmentDO> attainmentDOList = NewHelper.excelToDb(file.getInputStream(),batchRepository,courseRepository,batch,courseName,regulation);
            // System.out.println(studentCourseEntityList);
            List<AttainmentEntity> attainmentEntityList=new ArrayList<>();
            for(int i=0; i<attainmentDOList.size() ; i++){
                attainmentEntityList.add(attainmentConvert.convert2AttainmentEntity(attainmentDOList.get(i)));
            }
            attainmentRepository.saveAll(attainmentEntityList);

            assignEntity.setStatus("UPLOADED");
            assignRepository.save(assignEntity);

        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    @Transactional
    public void deletebatchcourseregulation( String batch, String courseName,String regulation)
    {
        System.out.println(batch+" "+courseName);
        System.out.println(batchRepository.getByBatch(batch)+" "+courseRepository.getByCourseName(courseName));
        if(!attainmentRepository.existsByBatchidAndCourseid(batchRepository.getByBatch(batch),courseRepository.getByCourseName(courseName)))
        {
            throw new NoSuchCourseNameExistForGivenBatchException(batch,courseName);
        }
        System.out.println(batchRepository.getByBatch(batch)+" "+courseRepository.getByCourseName(courseName));
        attainmentRepository.deleteAllByBatchidAndCourseid(batchRepository.getByBatch(batch),courseRepository.getByCourseName(courseName));

        AssignEntity assignEntity = assignRepository.getByBatchidAndCourseid(batchRepository.getByBatch(batch),courseRepository.getByCourseName(courseName));
        assignEntity.setStatus("NA");
        assignRepository.save(assignEntity);

    }

    public List<AttainmentDO> getAllAttinmentsOfBatch(String batch){
        List<AttainmentEntity> attainmentEntityList = attainmentRepository.getAllByBatchid(batchRepository.getByBatch(batch));
        List<AttainmentDO> attainmentDOList = new ArrayList<>();;
        for(int h = 0 ; h < attainmentEntityList.size() ; h++){
            attainmentDOList.add(attainmentConvert.convert2AttainmentDO(attainmentEntityList.get(h)));
        }
        return attainmentDOList;
    }
    public List<AttainmentDO> getAttinmentsOfBatch(String batch,String courseid){
        List<AttainmentEntity> attainmentEntityList = attainmentRepository.getAllByBatchidAndCourseid(batchRepository.getByBatch(batch),courseRepository.getByCourseid(courseid));
        List<AttainmentDO> attainmentDOList = new ArrayList<>();;
        for(int h = 0 ; h < attainmentEntityList.size() ; h++){
            attainmentDOList.add(attainmentConvert.convert2AttainmentDO(attainmentEntityList.get(h)));
        }
        return attainmentDOList;
    }

    public List<CourseDO> getuploadedcourses(String batch){

        List<AttainmentEntity> attainmentEntityList = attainmentRepository.getAllByBatchid(batchRepository.getByBatch(batch));
        Set<CourseDO> courseDOs = new HashSet<CourseDO>();

        for(int h=0; h < attainmentEntityList.size(); h++ ){
            courseDOs.add(courseConvert.convert2CourseDO(attainmentEntityList.get(h).getCourseid()));
        }

        List<CourseDO> courseDOList = new ArrayList<>();;

        for (CourseDO i : courseDOs) {
            courseDOList.add(i);
        }
        return courseDOList;
    }


    public List<AttainmentReportDO> getattainmentreport(String batch){
        Set<CourseEntity> courseset = new HashSet<CourseEntity>();
        List<AttainmentEntity> attainmentEntityList = attainmentRepository.getAllByBatchid(batchRepository.getByBatch(batch));
        for(int h=0; h<attainmentEntityList.size() ; h++){
            courseset.add(attainmentEntityList.get(h).getCourseid());
        }
        attainmentEntityList.clear();
        List<AttainmentReportDO> attainmentReportDOList =new ArrayList<>();

        for (CourseEntity courseEntity : courseset){
            attainmentEntityList = attainmentRepository.getAllByBatchidAndCourseid(batchRepository.getByBatch(batch),courseEntity);

            AttainmentReportDO attainmentReportDO = new AttainmentReportDO();

            String courseid=courseEntity.getCourseid();
            attainmentReportDO.setCourse(courseid.substring(0,5));
            attainmentReportDO.setSubject(courseEntity.getCourseName());

            float cosum=0,cocount=0;
            for(int h=0; h<attainmentEntityList.size() ;h++){
                if(attainmentEntityList.get(h).getCOtype().equals("CO1")){
                    attainmentReportDO.setCO1(attainmentEntityList.get(h).getCOvalue());
                    cosum=cosum+attainmentEntityList.get(h).getCOvalue();
                    cocount=cocount+1;
                }else if(attainmentEntityList.get(h).getCOtype().equals("CO2")){
                    attainmentReportDO.setCO2(attainmentEntityList.get(h).getCOvalue());
                    cosum=cosum+attainmentEntityList.get(h).getCOvalue();
                    cocount=cocount+1;
                }else if(attainmentEntityList.get(h).getCOtype().equals("CO3")){
                    attainmentReportDO.setCO3(attainmentEntityList.get(h).getCOvalue());
                    cosum=cosum+attainmentEntityList.get(h).getCOvalue();
                    cocount=cocount+1;
                }else if(attainmentEntityList.get(h).getCOtype().equals("CO4")){
                    attainmentReportDO.setCO4(attainmentEntityList.get(h).getCOvalue());
                    cosum=cosum+attainmentEntityList.get(h).getCOvalue();
                    cocount=cocount+1;
                }else if(attainmentEntityList.get(h).getCOtype().equals("CO5")){
                    attainmentReportDO.setCO5(attainmentEntityList.get(h).getCOvalue());
                    cosum=cosum+attainmentEntityList.get(h).getCOvalue();
                    cocount=cocount+1;
                }
                if(attainmentEntityList.get(h).getCOtype().equals("RESULT")){
                    attainmentReportDO.setPO1(attainmentEntityList.get(h).getPO1());
                    attainmentReportDO.setPO2(attainmentEntityList.get(h).getPO2());
                    attainmentReportDO.setPO3(attainmentEntityList.get(h).getPO3());
                    attainmentReportDO.setPO4(attainmentEntityList.get(h).getPO4());
                    attainmentReportDO.setPO5(attainmentEntityList.get(h).getPO5());
                    attainmentReportDO.setPO6(attainmentEntityList.get(h).getPO6());
                    attainmentReportDO.setPO7(attainmentEntityList.get(h).getPO7());
                    attainmentReportDO.setPO8(attainmentEntityList.get(h).getPO8());
                    attainmentReportDO.setPO9(attainmentEntityList.get(h).getPO9());
                    attainmentReportDO.setPO10(attainmentEntityList.get(h).getPO10());
                    attainmentReportDO.setPO11(attainmentEntityList.get(h).getPO11());
                    attainmentReportDO.setPO12(attainmentEntityList.get(h).getPO12());
                    attainmentReportDO.setPSO1(attainmentEntityList.get(h).getPSO1());
                    attainmentReportDO.setPSO2(attainmentEntityList.get(h).getPSO2());
                }
            }
            float attainmentaverage=(float)cosum/cocount;

            if(attainmentaverage>=2){
                attainmentReportDO.setAttained("YES");
            }else{
                attainmentReportDO.setAttained("NO");
            }
            attainmentReportDO.setAverageCOattainment((Float) attainmentaverage);
            //attainmentReportDO.setResult("Direct Attainment");

            attainmentReportDOList.add(attainmentReportDO);
        }
        AttainmentReportDO attainmentReportDO = new AttainmentReportDO();
        float PO1sum=0,PO1count=0,PO2sum=0,PO2count=0,PO3sum=0,PO3count=0,PO4sum=0,PO4count=0;
        float PO5sum=0,PO5count=0,PO6sum=0,PO6count=0,PO7sum=0,PO7count=0,PO8sum=0,PO8count=0;
        float PO9sum=0,PO9count=0,PO10sum=0,PO10count=0,PO11sum=0,PO11count=0,PO12sum=0,PO12count=0;
        float PSO1sum=0,PSO1count=0,PSO2sum=0,PSO2count=0;
        for(int h=0; h<attainmentReportDOList.size() ; h++){
            System.out.println(h);
            if(!attainmentReportDOList.get(h).getPO1().equals((float)0)){
                PO1sum=PO1sum+attainmentReportDOList.get(h).getPO1();
                PO1count=PO1count+1;
            }
            if(!attainmentReportDOList.get(h).getPO2().equals((float)0)){
                PO2sum=PO2sum+attainmentReportDOList.get(h).getPO2();
                PO2count=PO2count+1;
            }
            if(!attainmentReportDOList.get(h).getPO3().equals((float)0)){
                PO3sum=PO3sum+attainmentReportDOList.get(h).getPO3();
                PO3count=PO3count+1;
            }
            if(!attainmentReportDOList.get(h).getPO4().equals((float)0)){
                PO4sum=PO4sum+attainmentReportDOList.get(h).getPO4();
                PO4count=PO4count+1;
            }
            if(!attainmentReportDOList.get(h).getPO5().equals((float)0)){
                PO5sum=PO5sum+attainmentReportDOList.get(h).getPO5();
                PO5count=PO5count+1;
            }
            if(!attainmentReportDOList.get(h).getPO6().equals((float)0)){
                PO6sum=PO6sum+attainmentReportDOList.get(h).getPO6();
                PO6count=PO6count+1;
            }
            if(!attainmentReportDOList.get(h).getPO7().equals((float)0)){
                PO7sum=PO7sum+attainmentReportDOList.get(h).getPO7();
                PO7count=PO7count+1;
            }
            if(!attainmentReportDOList.get(h).getPO8().equals((float)0)){
                PO8sum=PO8sum+attainmentReportDOList.get(h).getPO8();
                PO8count=PO8count+1;
            }
            if(!attainmentReportDOList.get(h).getPO9().equals((float)0)){
                PO9sum=PO9sum+attainmentReportDOList.get(h).getPO9();
                PO9count=PO9count+1;
            }
            if(!attainmentReportDOList.get(h).getPO10().equals((float)0)){
                PO10sum=PO10sum+attainmentReportDOList.get(h).getPO10();
                PO10count=PO10count+1;
            }
            if(!attainmentReportDOList.get(h).getPO11().equals((float)0)){
                PO11sum=PO11sum+attainmentReportDOList.get(h).getPO11();
                PO11count=PO11count+1;
            }
            if(!attainmentReportDOList.get(h).getPO12().equals((float)0)){
                PO12sum=PO12sum+attainmentReportDOList.get(h).getPO12();
                PO12count=PO12count+1;
            }
            if(!attainmentReportDOList.get(h).getPSO1().equals((float)0)){
                PSO1sum=PSO1sum+attainmentReportDOList.get(h).getPSO1();
                PSO1count=PSO1count+1;
            }
            if(!attainmentReportDOList.get(h).getPSO2().equals((float)0)){
                PSO2sum=PSO2sum+attainmentReportDOList.get(h).getPSO2();
                PSO2count=PSO2count+1;
            }

        }
        attainmentReportDO.setResult("Direct Attainment");
        //System.out.println(PO1count);
        //System.out.println(PO1sum);
        if(PO1count!=0) {
            attainmentReportDO.setPO1((float) PO1sum / PO1count);
        }else{
            attainmentReportDO.setPO1((float)0);
        }
        if(PO2count!=0){
            attainmentReportDO.setPO2((float)PO2sum/PO2count);
        }else{
            attainmentReportDO.setPO2((float)0);
        }
        if(PO3count!=0){
            attainmentReportDO.setPO3((float)PO3sum/PO3count);
        }else{
            attainmentReportDO.setPO3((float)0);
        }
        if(PO4count!=0){
            attainmentReportDO.setPO4((float)PO4sum/PO4count);
        }else{
            attainmentReportDO.setPO4((float)0);
        }
        if(PO5count!=0) {
            attainmentReportDO.setPO5((float) PO5sum / PO5count);
        }else{
            attainmentReportDO.setPO5((float)0);
        }
        if(PO6count!=0){
            attainmentReportDO.setPO6((float)PO6sum/PO6count);
        }else{
            attainmentReportDO.setPO6((float)0);
        }
        if(PO7count!=0) {
            attainmentReportDO.setPO7((float) PO7sum / PO7count);
        }else{
            attainmentReportDO.setPO7((float)0);
        }
        if(PO8count!=0) {
            attainmentReportDO.setPO8((float) PO8sum / PO8count);
        }else{
            attainmentReportDO.setPO8((float)0);
        }
        if(PO9count!=0) {
            attainmentReportDO.setPO9((float) PO9sum / PO9count);
        }else{
            attainmentReportDO.setPO9((float)0);
        }
        if(PO10count!=0) {
            attainmentReportDO.setPO10((float) PO10sum / PO10count);
        }else{
            attainmentReportDO.setPO10((float)0);
        }
        if(PO11count!=0) {
            attainmentReportDO.setPO11((float) PO11sum / PO11count);
        }else{
            attainmentReportDO.setPO11((float)0);
        }
        if(PO12count!=0) {
            attainmentReportDO.setPO12((float) PO12sum / PO12count);
        }else{
            attainmentReportDO.setPO12((float)0);
        }
        if(PSO1count!=0) {
            attainmentReportDO.setPSO1((float) PSO1sum / PSO1count);
        }else{
            attainmentReportDO.setPSO1((float)0);
        }
        if(PSO2count!=0) {
            attainmentReportDO.setPSO2((float) PSO2sum / PSO2count);
        }else{
            attainmentReportDO.setPSO2((float)0);
        }

        attainmentReportDOList.add(attainmentReportDO);

        return attainmentReportDOList;
    }

    public void assignCourse(AssignDO assignDO){
        AssignEntity assignEntity = assignConvert.convert2AssignEntity(assignDO);
        assignRepository.save(assignEntity);
    }

    public void extendTime(AssignDO assignDO){
        AssignEntity assignEntity = assignRepository.getByBatchidAndCourseid(batchRepository.getByBatch(assignDO.getBatch()),courseRepository.getByCourseid(assignDO.getCourseId()));
        assignEntity.setDate(assignDO.getDate());
        assignEntity.setTime(assignDO.getTime());
        assignRepository.save(assignEntity);
    }

}
