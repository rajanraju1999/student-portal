package com.example.Studentdataportal.Util;

import com.example.Studentdataportal.DataObjects.StudentCourseDO;
import com.example.Studentdataportal.DataObjects.StudentDO;
import com.example.Studentdataportal.Entitis.CgpaAndSgpaEntity;
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
import java.util.*;

public class Helper {



    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    // static String[] HEADERs = { "studentid", "courseid", "grade", "semester" };


    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static List<StudentCourseDO> excelToDbadvsupAndsup(InputStream is, StudentRepository studentRepository, CourseRepository courseRepository, StudentCourseRepository studentCourseRepositiry, Long sem, String type,String date,String regulation) {
        System.out.println(type);

        DataFormatter formatter = new DataFormatter();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            List<StudentCourseDO> studentCourseDOList = new ArrayList<>();

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
            for (int i = 1; i < sub.size()-2; i++) {
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

                for (int i = 1; i < grades.size()-2; i++) {
                    StudentCourseDO studentCourseDO = new StudentCourseDO();
                    //if student does not  exist in database  but exists in Excel
                    if(grades.get(0)==null||grades.get(0).trim().isEmpty())
                    {
                        throw new EmptyFieldException();
                    }
                    if (!studentRepository.existsById(grades.get(0))) {
                        throw new StudentNotExistsInDBException(grades.get(0));
                    }
                    if(!grades.get(i).isEmpty()) {
                        studentCourseDO.setStudentid(studentRepository.getByRollnumber(grades.get(0)).getRollnumber());
                        studentCourseDO.setCourseid(courseRepository.getByCourseNameAndCourseRegulation(sub.get(i),regulation).getCourseid());
                        studentCourseDO.setGrade(grades.get(i));
                        studentCourseDO.setSemester(sem);
                        studentCourseDO.setExamdate(date);
                        studentCourseDO.setSgpa(grades.get(grades.size()-2));
                        studentCourseDO.setCgpa(grades.get(grades.size()-1));
                        studentCourseDOList.add(studentCourseDO);
                    }
                    // System.out.println(studentCourseEntityList);
                }
            }
            workbook.close();
            return studentCourseDOList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }


    }







    public static List<StudentCourseDO> excelToDb(InputStream is, StudentRepository studentRepository, CourseRepository courseRepository, StudentCourseRepository studentCourseRepositiry,BatchRepository batchRepository,String batch, Long sem, String date,String regulation) {

        DataFormatter formatter = new DataFormatter();

        try {
            // "is" is the file to be read.
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            //"sheet" contains the data of 1st sheet in workbook.
            XSSFSheet sheet = workbook.getSheetAt(0);
            //"rows" is an iterator used to iterate through rows in "sheet".
            Iterator<Row> rows = sheet.iterator();

            List<StudentCourseDO> studentCourseDOList = new ArrayList<>();

            Set<StudentEntity> hash_Set = new HashSet<StudentEntity>();




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
            for (int i = 1; i < sub.size()-2; i++) {
                if (!courseRepository.existsByCourseNameAndCourseRegulation(sub.get(i),regulation)) {
                    String string = sub.get(i);
                    throw new CourseNameErrorInExcelException(string,regulation);
                }
            }
            if( !sub.get(sub.size()-2).equals("SGPA") ){
                throw new SGPAandCGPAcolumnNameErrorInExcelException();
            }

            if( !sub.get(sub.size()-1).equals("CGPA") ){
                throw new SGPAandCGPAcolumnNameErrorInExcelException();
            }


            //rollnumber,a,b,a,c
            List<String> grades = new ArrayList<>();

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                Iterator<Cell> cellsInRow = currentRow.iterator();


                //int cellIdx = 0;
                grades.clear();
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                  /*  if(currentCell == null || currentCell.getCellType() == CellType.BLANK){
                        throw new EmptyFieldException();
                    }*/
                    // if(!(currentCell ==null))
                    //{
                    grades.add(formatter.formatCellValue(currentCell));
                    //}
                    //cellIdx++;
                }
                // System.out.println(grades);

                // check if the student exist in the student records.
                if (!studentRepository.existsById(grades.get(0))) {
                    throw new StudentNotExistsInDBException(grades.get(0));
                }

                if ( !studentRepository.getByRollnumber(grades.get(0)).getBatchid().getBatch().equals(batch)) {
                    throw new StudentFromOtherBatchExistsException();
                }

                if(studentCourseRepositiry.existsByStudentidAndSemester(studentRepository.getByRollnumber(grades.get(0)),sem)){
                    throw new AlreadySemesterResultsExistForGivenBatchException(batch,sem);
                }
                for (int i = 1; i < grades.size()-2; i++) {
                    StudentCourseDO studentCourseDO = new StudentCourseDO();
                    //if student does not  exist in database  but exists in Excel

                    if(studentCourseRepositiry.existsByStudentidAndCourseid(studentRepository.getByRollnumber(grades.get(0)),courseRepository.getByCourseNameAndCourseRegulation(sub.get(i),regulation))){
                        StudentEntity studentEntity=studentRepository.getByRollnumber(grades.get(0));
                        CourseEntity courseEntity=courseRepository.getByCourseNameAndCourseRegulation(sub.get(i),regulation);
                        throw new AlreadyEntryExistsException(studentEntity.getRollnumber(),courseEntity.getCourseid());
                    }

                    studentCourseDO.setStudentid(studentRepository.getByRollnumber(grades.get(0)).getRollnumber());
                    studentCourseDO.setCourseid(courseRepository.getByCourseNameAndCourseRegulation(sub.get(i),regulation).getCourseid());
                    studentCourseDO.setGrade(grades.get(i));
                    studentCourseDO.setSemester(sem);
                    studentCourseDO.setExamdate(date);
                    studentCourseDO.setSgpa(grades.get(grades.size()-2));
                    studentCourseDO.setCgpa(grades.get(grades.size()-1));
                    studentCourseDOList.add(studentCourseDO);

                    // System.out.println(studentCourseEntityList);
                }

                  /*  CgpaAndSgpaEntity cgpaAndSgpaEntity = new CgpaAndSgpaEntity();
                    cgpaAndSgpaEntity.setStudentid(studentRepository.getByRollnumber(grades.get(0)));
                    cgpaAndSgpaEntity.setSgpa1(grades.get(grades.size()-1));
                    cgpaAndSgpaEntity.setCgpa(grades.get(grades.size()-2));*/
                hash_Set.add(studentRepository.getByRollnumber(grades.get(0)));
            }
            List<StudentEntity> studentEntityList=studentRepository.getByBatchid(batchRepository.getByBatch(batch));
            for(int h=0; h<studentEntityList.size() ; h++){
                if(!hash_Set.contains(studentEntityList.get(h)))
                    throw new NotAllStudentsresultsExistsInExcelException();
            }
            workbook.close();
            return studentCourseDOList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }


    }
    public static List<StudentDO> excelToDbcheckStudents(InputStream is, StudentRepository studentRepository, CourseRepository courseRepository, StudentCourseRepository studentCourseRepositiry,StudentConvert studentConvert,String batch,String regulation) {

        DataFormatter formatter = new DataFormatter();

        try {
            // "is" is the file to be read.
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            //"sheet" contains the data of 1st sheet in workbook.
            XSSFSheet sheet = workbook.getSheetAt(0);
            //"rows" is an iterator used to iterate through rows in "sheet".
            Iterator<Row> rows = sheet.iterator();

            List<StudentDO> studentDOList = new ArrayList<>();




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
            for (int i = 1; i < sub.size()-2; i++) {
                if (!courseRepository.existsByCourseNameAndCourseRegulation(sub.get(i),regulation)) {
                    String string = sub.get(i);
                    throw new CourseNameErrorInExcelException(string,regulation);
                }
            }
            if( !sub.get(sub.size()-2).equals("SGPA") ){
                throw new SGPAandCGPAcolumnNameErrorInExcelException();
            }

            if( !sub.get(sub.size()-1).equals("CGPA") ){
                throw new SGPAandCGPAcolumnNameErrorInExcelException();
            }


            //rollnumber,a,b,a,c
            List<String> grades = new ArrayList<>();

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                Iterator<Cell> cellsInRow = currentRow.iterator();


                //int cellIdx = 0;
                grades.clear();
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                  /*  if(currentCell == null || currentCell.getCellType() == CellType.BLANK){
                        throw new EmptyFieldException();
                    }*/
                    // if(!(currentCell ==null))
                    //{
                    grades.add(formatter.formatCellValue(currentCell));
                    //}
                    //cellIdx++;
                }
                // System.out.println(grades);

                // check if the student exist in the student records.
                if (!studentRepository.existsById(grades.get(0))) {
                    throw new StudentNotExistsInDBException(grades.get(0));
                }

                if ( !studentRepository.getByRollnumber(grades.get(0)).getBatchid().getBatch().equals(batch)) {
                    studentDOList.add(studentConvert.convert2StudentDO(studentRepository.getByRollnumber(grades.get(0))));
                }

            }
            workbook.close();
            return studentDOList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }


    }

    public static List<StudentDO> excelToDbcheckStudents1(InputStream is, StudentRepository studentRepository, CourseRepository courseRepository, StudentCourseRepository studentCourseRepositiry,StudentConvert studentConvert,BatchRepository batchRepository,String batch, String regulation) {

        DataFormatter formatter = new DataFormatter();

        try {
            // "is" is the file to be read.
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            //"sheet" contains the data of 1st sheet in workbook.
            XSSFSheet sheet = workbook.getSheetAt(0);
            //"rows" is an iterator used to iterate through rows in "sheet".
            Iterator<Row> rows = sheet.iterator();

            List<StudentDO> studentDOList = new ArrayList<>();

            Set<StudentEntity> hash_Set = new HashSet<StudentEntity>();
            List<StudentEntity> studentEntityList = new ArrayList<>();


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
            for (int i = 1; i < sub.size()-2; i++) {
                if (!courseRepository.existsByCourseNameAndCourseRegulation(sub.get(i),regulation)) {
                    String string = sub.get(i);
                    throw new CourseNameErrorInExcelException(string,regulation);
                }
            }
            if( !sub.get(sub.size()-2).equals("SGPA") ){
                throw new SGPAandCGPAcolumnNameErrorInExcelException();
            }

            if( !sub.get(sub.size()-1).equals("CGPA") ){
                throw new SGPAandCGPAcolumnNameErrorInExcelException();
            }


            //rollnumber,a,b,a,c
            List<String> grades = new ArrayList<>();

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                Iterator<Cell> cellsInRow = currentRow.iterator();


                //int cellIdx = 0;
                grades.clear();
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                  /*  if(currentCell == null || currentCell.getCellType() == CellType.BLANK){
                        throw new EmptyFieldException();
                    }*/
                    // if(!(currentCell ==null))
                    //{
                    grades.add(formatter.formatCellValue(currentCell));
                    //}
                    //cellIdx++;
                }
                // System.out.println(grades);

                // check if the student exist in the student records.
                if (!studentRepository.existsById(grades.get(0))) {
                    throw new StudentNotExistsInDBException(grades.get(0));
                }
                hash_Set.add(studentRepository.getByRollnumber(grades.get(0)));
            }
            studentEntityList=studentRepository.getByBatchid(batchRepository.getByBatch(batch));
            for(int h=0; h<studentEntityList.size() ; h++){
                if(!hash_Set.contains(studentEntityList.get(h)))
                    studentDOList.add(studentConvert.convert2StudentDO(studentEntityList.get(h)));
            }
            workbook.close();
            return studentDOList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }


    }


    public static List<StudentCourseDO> excelToDb(InputStream is, StudentRepository studentRepository, CourseRepository courseRepository, StudentCourseRepository studentCourseRepositiry, Long sem, String type, String date, String regulation) {
        System.out.println(type);

        DataFormatter formatter = new DataFormatter();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            List<StudentCourseDO> studentCourseDOList = new ArrayList<>();

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
            for (int i = 1; i < sub.size()-2; i++) {
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
                  /*  if(currentCell == null || currentCell.getCellType() == CellType.BLANK){
                        throw new EmptyFieldException();
                    }*/
                   // if(!(currentCell ==null))
                    //{
                        grades.add(formatter.formatCellValue(currentCell));
                    //}
                    //cellIdx++;
                }
                // System.out.println(grades);

                for (int i = 1; i < grades.size()-2; i++) {
                    StudentCourseDO studentCourseDO = new StudentCourseDO();
                    //if student does not  exist in database  but exists in Excel
                    if (!studentRepository.existsById(grades.get(0))) {
                        throw new StudentNotExistsInDBException(grades.get(0));
                    }


                    if(studentCourseRepositiry.existsByStudentidAndCourseid(studentRepository.getByRollnumber(grades.get(0)),courseRepository.getByCourseNameAndCourseRegulation(sub.get(i),regulation))){

                        StudentEntity studentEntity=studentRepository.getByRollnumber(grades.get(0));
                        CourseEntity courseEntity=courseRepository.getByCourseNameAndCourseRegulation(sub.get(i),regulation);
                        throw new AlreadyEntryExistsException(studentEntity.getRollnumber(),courseEntity.getCourseid());
                    }
                    studentCourseDO.setStudentid(studentRepository.getByRollnumber(grades.get(0)).getRollnumber());
                    studentCourseDO.setCourseid(courseRepository.getByCourseNameAndCourseRegulation(sub.get(i),regulation).getCourseid());
                    studentCourseDO.setGrade(grades.get(i));
                    studentCourseDO.setSemester(sem);
                    studentCourseDO.setExamdate(date);
                    studentCourseDO.setSgpa(grades.get(grades.size()-2));
                    studentCourseDO.setCgpa(grades.get(grades.size()-1));
                    studentCourseDOList.add(studentCourseDO);

                    // System.out.println(studentCourseEntityList);
                }

                  /*  CgpaAndSgpaEntity cgpaAndSgpaEntity = new CgpaAndSgpaEntity();
                    cgpaAndSgpaEntity.setStudentid(studentRepository.getByRollnumber(grades.get(0)));
                    cgpaAndSgpaEntity.setSgpa1(grades.get(grades.size()-1));
                    cgpaAndSgpaEntity.setCgpa(grades.get(grades.size()-2));*/

            }
            workbook.close();
            return studentCourseDOList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }


    }





    public static List<StudentDO> studentExcelToDB(InputStream is, StudentRepository studentRepository, BatchRepository batchRepository) {


        DataFormatter formatter = new DataFormatter();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheetAt(0);
            if(sheet==null)
            {
                throw new SheetProblemException();
            }
            Iterator<Row> rows = sheet.iterator();
            List<StudentDO> studentDOList = new ArrayList<>();

            rows.next();
            while (rows.hasNext()) {

                Row currentRow = rows.next();

                Iterator<Cell> cellsInRow = currentRow.iterator();
                StudentDO studentDO = new StudentDO();
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
                            studentDO.setRollnumber(formatter.formatCellValue(currentCell));
                            break;
                        case 1:
                            studentDO.setName(formatter.formatCellValue(currentCell));
                            break;
                        case 2:
                            studentDO.setEmailid(formatter.formatCellValue(currentCell));
                            break;
                        //case 3:
                          //  studentEntity.setYear(formatter.formatCellValue(currentCell));
                          //  break;
                        case 3:
                            studentDO.setSection(formatter.formatCellValue(currentCell));
                            break;
                        case 4:
                            studentDO.setBatchid(batchRepository.getByBatch(formatter.formatCellValue(currentCell)).getBatch());
                            break;
                        case 5:
                            studentDO.setYearofjoining(formatter.formatCellValue(currentCell));
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                studentDOList.add(studentDO);
            }
            workbook.close();
            //studentEntityList.remove(studentEntityList.size() - 1);
            return studentDOList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }


    }
}
