package com.example.Studentdataportal.zattainmentmodule.Util;

import com.example.Studentdataportal.Entitis.CourseEntity;
import com.example.Studentdataportal.Entitis.StudentCourseEntity;
import com.example.Studentdataportal.Entitis.StudentEntity;
import com.example.Studentdataportal.Repositorys.BatchRepository;
import com.example.Studentdataportal.Repositorys.CourseRepository;
import com.example.Studentdataportal.Repositorys.StudentCourseRepository;
import com.example.Studentdataportal.Repositorys.StudentRepository;
import com.example.Studentdataportal.exception.*;
import com.example.Studentdataportal.zattainmentmodule.DataObjects.AttainmentDO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NewHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }
    public static List<AttainmentDO> excelToDb(InputStream is, BatchRepository batchRepository, CourseRepository courseRepository, String batch, String courseName , String regulation) {
        DataFormatter formatter = new DataFormatter();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheetAt(0);
            if(sheet==null)
            {
                throw new SheetProblemException();
            }

            Iterator<Row> rows = sheet.iterator();

            List<AttainmentDO> attainmentDOList = new ArrayList<>();

            rows.next();
            while (rows.hasNext()) {

                Row currentRow = rows.next();

                Iterator<Cell> cellsInRow = currentRow.iterator();
                AttainmentDO attainmentDO = new AttainmentDO();
                attainmentDO.setBatchid(batch);
                CourseEntity courseEntity = courseRepository.getByCourseNameAndCourseRegulation(courseName,regulation);
                attainmentDO.setCourseid(courseEntity.getCourseid());
                int cellIdx = 0;
                while(cellsInRow.hasNext()) {

                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            attainmentDO.setCOtype(formatter.formatCellValue(currentCell));
                            break;
                        case 1:
                            attainmentDO.setCOvalue((float)currentCell.getNumericCellValue());
                            break;
                        case 2:
                            attainmentDO.setPO1((float) currentCell.getNumericCellValue());
                            break;
                        case 3:
                            attainmentDO.setPO2((float) currentCell.getNumericCellValue());
                            break;
                        case 4:
                            //attainmentDO.setBatchid(batchRepository.getByBatch(formatter.formatCellValue(currentCell)));
                            attainmentDO.setPO3((float) currentCell.getNumericCellValue());
                            break;
                        case 5:
                            //studentEntity.setYearofjoining(formatter.formatCellValue(currentCell));
                            attainmentDO.setPO4((float) currentCell.getNumericCellValue());
                            break;
                        case 6:
                            attainmentDO.setPO5((float) currentCell.getNumericCellValue());
                            break;
                        case 7:
                            attainmentDO.setPO6((float) currentCell.getNumericCellValue());
                            break;
                        case 8:
                            attainmentDO.setPO7((float) currentCell.getNumericCellValue());
                            break;
                        case 9:
                            attainmentDO.setPO8((float) currentCell.getNumericCellValue());
                            break;
                        case 10:
                            attainmentDO.setPO9((float) currentCell.getNumericCellValue());
                            break;
                        case 11:
                            attainmentDO.setPO10((float) currentCell.getNumericCellValue());
                            break;
                        case 12:
                            attainmentDO.setPO11((float) currentCell.getNumericCellValue());
                            break;
                        case 13:
                            attainmentDO.setPO12((float) currentCell.getNumericCellValue());
                            break;
                        case 14:
                            attainmentDO.setPSO1((float) currentCell.getNumericCellValue());
                            break;
                        case 15:
                            attainmentDO.setPSO2((float) currentCell.getNumericCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                attainmentDOList.add(attainmentDO);
            }
            workbook.close();
            //studentEntityList.remove(studentEntityList.size() - 1);
            return attainmentDOList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }

    }


}
