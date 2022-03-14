package com.example.Studentdataportal.Util;

import com.example.Studentdataportal.Entitis.CourseEntity;
import com.example.Studentdataportal.Entitis.StudentCourseEntity;
import com.example.Studentdataportal.Entitis.StudentEntity;
import com.example.Studentdataportal.Repositorys.BatchRepository;
import com.example.Studentdataportal.Repositorys.CourseRepository;
import com.example.Studentdataportal.Repositorys.StudentCourseRepository;
import com.example.Studentdataportal.Repositorys.StudentRepository;
import com.example.Studentdataportal.exception.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Helper {



    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    // static String[] HEADERs = { "studentid", "courseid", "grade", "semester" };


    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static List<StudentCourseEntity> excelToDbadvsupAndsup(InputStream is, StudentRepository studentRepository, CourseRepository courseRepository, StudentCourseRepository studentCourseRepositiry, Long sem, String type,String date,String regulation) {
        System.out.println(type);

        DataFormatter formatter = new DataFormatter();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            List<StudentCourseEntity> studentCourseEntityList = new ArrayList<>();

            //rollnumber,a,b,a,c
            List<String> grades = new ArrayList<>();


            //taking first row(headers) into a list
            List<String> sub = new ArrayList<>();
            Row currentRow1 = rows.next();
            Iterator<Cell> cellsInRow1 = currentRow1.iterator();
            while (cellsInRow1.hasNext()) {
                Cell currentCell = cellsInRow1.next();
                if(currentCell == null || currentCell.getCellType() == CellType.BLANK){
                    throw new EmptyFieldException();
                }
                sub.add(formatter.formatCellValue(currentCell));
            }
            // System.out.println(sub);

            //excel name error handling
            for (int i = 1; i < sub.size(); i++) {
                if (!courseRepository.existsByCourseNameAndCourseRegulation(sub.get(i),regulation)) {
                    String string = sub.get(i);
                    throw new CourseNameErrorInExcelException(string,regulation);
                }
            }

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                Iterator<Cell> cellsInRow = currentRow.iterator();


                //int cellIdx = 0;
                grades.clear();
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    // if(!(currentCell ==null))
                    //{
                    grades.add(formatter.formatCellValue(currentCell));
                    //}
                    //cellIdx++;
                }
                // System.out.println(grades);

                for (int i = 1; i < grades.size(); i++) {
                    StudentCourseEntity studentCourseEntity = new StudentCourseEntity();
                    //if student does not  exist in database  but exists in Excel
                    if(grades.get(0)==null||grades.get(0).trim().isEmpty())
                    {
                        throw new EmptyFieldException();
                    }
                    if (!studentRepository.existsById(grades.get(0))) {
                        throw new StudentNotExistsInDBException(grades.get(0));
                    }
                    if(!grades.get(i).isEmpty()) {
                        studentCourseEntity.setStudentid(studentRepository.getByRollnumber(grades.get(0)));
                        studentCourseEntity.setCourseid(courseRepository.getByCourseNameAndCourseRegulation(sub.get(i),regulation));
                        studentCourseEntity.setGrade(grades.get(i));
                        studentCourseEntity.setSemester(sem);
                        studentCourseEntity.setExamdate(date);
                        studentCourseEntityList.add(studentCourseEntity);
                    }
                    // System.out.println(studentCourseEntityList);
                }
            }
            workbook.close();
            return studentCourseEntityList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }


    }











    public static List<StudentCourseEntity> excelToDb(InputStream is, StudentRepository studentRepository, CourseRepository courseRepository, StudentCourseRepository studentCourseRepositiry, Long sem, String type,String date,String regulation) {
        System.out.println(type);

        DataFormatter formatter = new DataFormatter();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            List<StudentCourseEntity> studentCourseEntityList = new ArrayList<>();

            //rollnumber,a,b,a,c
            List<String> grades = new ArrayList<>();


            //taking first row(headers) into a list
            List<String> sub = new ArrayList<>();
            Row currentRow1 = rows.next();
            Iterator<Cell> cellsInRow1 = currentRow1.iterator();
            while (cellsInRow1.hasNext()) {
                Cell currentCell = cellsInRow1.next();
                if(currentCell == null || currentCell.getCellType() == CellType.BLANK){
                    throw new EmptyFieldException();
                }
                sub.add(formatter.formatCellValue(currentCell));
            }
            // System.out.println(sub);

            //excel name error handling
            for (int i = 1; i < sub.size(); i++) {
                if (!courseRepository.existsByCourseNameAndCourseRegulation(sub.get(i),regulation)) {
                    String string = sub.get(i);
                    throw new CourseNameErrorInExcelException(string,regulation);
                }
            }


            while (rows.hasNext()) {
                Row currentRow = rows.next();
                Iterator<Cell> cellsInRow = currentRow.iterator();


                //int cellIdx = 0;
                grades.clear();
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    if(currentCell == null || currentCell.getCellType() == CellType.BLANK){
                        throw new EmptyFieldException();
                    }
                   // if(!(currentCell ==null))
                    //{
                        grades.add(formatter.formatCellValue(currentCell));
                    //}
                    //cellIdx++;
                }
                // System.out.println(grades);

                for (int i = 1; i < grades.size(); i++) {
                    StudentCourseEntity studentCourseEntity = new StudentCourseEntity();
                    //if student does not  exist in database  but exists in Excel
                    if (!studentRepository.existsById(grades.get(0))) {
                        throw new StudentNotExistsInDBException(grades.get(0));
                    }


                    if(studentCourseRepositiry.existsByStudentidAndCourseid(studentRepository.getByRollnumber(grades.get(0)),courseRepository.getByCourseNameAndCourseRegulation(sub.get(i),regulation))){

                        StudentEntity studentEntity=studentRepository.getByRollnumber(grades.get(0));
                        CourseEntity courseEntity=courseRepository.getByCourseNameAndCourseRegulation(sub.get(i),regulation);
                        throw new AlreadyEntryExistsException(studentEntity.getRollnumber(),courseEntity.getCourseid());
                    }
                    studentCourseEntity.setStudentid(studentRepository.getByRollnumber(grades.get(0)));
                    studentCourseEntity.setCourseid(courseRepository.getByCourseNameAndCourseRegulation(sub.get(i),regulation));
                    studentCourseEntity.setGrade(grades.get(i));
                    studentCourseEntity.setSemester(sem);
                    studentCourseEntity.setExamdate(date);
                    studentCourseEntityList.add(studentCourseEntity);

                    // System.out.println(studentCourseEntityList);
                }
            }
            workbook.close();
            return studentCourseEntityList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }


    }





    public static List<StudentEntity> studentExcelToDB(InputStream is, StudentRepository studentRepository, BatchRepository batchRepository) {


        DataFormatter formatter = new DataFormatter();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheetAt(0);
            if(sheet==null)
            {
                throw new SheetProblemException();
            }
            Iterator<Row> rows = sheet.iterator();
            List<StudentEntity> studentEntityList = new ArrayList<>();

            rows.next();
            while (rows.hasNext()) {

                Row currentRow = rows.next();

                Iterator<Cell> cellsInRow = currentRow.iterator();
                StudentEntity studentEntity = new StudentEntity();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {

                    Cell currentCell = cellsInRow.next();
                    if(currentCell == null || currentCell.getCellType() == CellType.BLANK){
                        throw new EmptyFieldException();
                    }
                    switch (cellIdx) {
                        case 0:
                            if(studentRepository.existsById(formatter.formatCellValue(currentCell)))
                            {
                                throw new AlreadyStudentExistsException(formatter.formatCellValue(currentCell));
                            }
                            studentEntity.setRollnumber(formatter.formatCellValue(currentCell));
                            break;
                        case 1:
                            studentEntity.setName(formatter.formatCellValue(currentCell));
                            break;
                        case 2:
                            studentEntity.setEmailid(formatter.formatCellValue(currentCell));
                            break;
                        //case 3:
                          //  studentEntity.setYear(formatter.formatCellValue(currentCell));
                          //  break;
                        case 3:
                            studentEntity.setSection(formatter.formatCellValue(currentCell));
                            break;
                        case 4:
                            studentEntity.setBatchid(batchRepository.getByBatch(formatter.formatCellValue(currentCell)));
                            break;
                        case 5:
                            studentEntity.setYearofjoining(formatter.formatCellValue(currentCell));
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                studentEntityList.add(studentEntity);
            }
            workbook.close();
            //studentEntityList.remove(studentEntityList.size() - 1);
            return studentEntityList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }


    }
}
