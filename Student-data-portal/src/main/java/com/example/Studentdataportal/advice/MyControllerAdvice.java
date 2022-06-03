package com.example.Studentdataportal.advice;

import com.example.Studentdataportal.Util.ResponseMessage;
import com.example.Studentdataportal.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.NoSuchElementException;

@ControllerAdvice
public class MyControllerAdvice {

    @ExceptionHandler(EmptyRollNumberException.class)
    public ResponseEntity<String> EmptyRollNumberException (EmptyRollNumberException emptyInputException)
    {
        return new ResponseEntity<>("Empty RollNumber ", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyStudentExistsException.class)
    public ResponseEntity<String>AlreadyStudentExistsException(AlreadyStudentExistsException exc)
    {
        return new ResponseEntity<>("allReady student Exists  "+exc.getString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyEmailExistsException.class)
    public ResponseEntity<String> AllReadyEmailExistsException(AlreadyEmailExistsException alReadyEmailExistsException)
    {
        return new ResponseEntity<>("allReady Email Exists  ", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> NoSuchElementException(NoSuchElementException noSuchElementException)
    {
        return new ResponseEntity<>(" No Such element", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyCourseIdException.class)
    public ResponseEntity<String> EmptyCourseIdException(EmptyCourseIdException emptyCourseIdException)
    {
        return new ResponseEntity<>(" Empty course Id ", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyCourseNameExistException.class)
    public ResponseEntity<String> AlreadyCourseNameExistException(AlreadyCourseNameExistException alreadyCourseNameExistException)
    {
        return new ResponseEntity<>("course Name Already exists", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyCourseIdExistException.class)
    public ResponseEntity<String> AlreadyCourseIdExistException(AlreadyCourseIdExistException alreadyCourseIdExistException)
    {
        return new ResponseEntity<>("course Id Already exists", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ResponseMessage> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("File too large!"));
    }
    @ExceptionHandler(CourseNameErrorInExcelException.class)
    public ResponseEntity<String> CourseNameErrorInExcelException(CourseNameErrorInExcelException exc) {
        return new ResponseEntity<>("course name in database doesnot match with course name in excelsheet "+exc.getString()+"\n OR "+exc.getString()+" does not exist with regulation "+exc.getString1()+" \n  OR "+exc.getString()+" does not exists in database ", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(StudentNotExistsInDBException.class)
    public ResponseEntity<String> StudentNotExistsInDBException(StudentNotExistsInDBException exc) {
        return new ResponseEntity<>("student " +exc.getString()+" details doe not exists in data base ", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NoDataSentException.class)
    public ResponseEntity<String> NoDataSentException(NoDataSentException exc) {
        return new ResponseEntity<>("No details are sent for updation", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AlreadyEntryExistsException.class)
    public ResponseEntity<String> AlreadyEntrytExistsException(AlreadyEntryExistsException exc) {
        return new ResponseEntity<>("already entry exists with this role number "+ exc.getString()+" and course id "+exc.getString1()+". Looks like uploading the results again.", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<String> EmptyFieldException(EmptyFieldException exc) {
        return new ResponseEntity<>("some required field is empty or some required field is absent", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(SheetProblemException.class)
    public ResponseEntity<String> SheetProblemException(SheetProblemException exc) {
        return new ResponseEntity<>("sheet name error or some sheet problem", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MultipleAttemptsPresentException.class)
    public ResponseEntity<String> MultipleAttemptsPresentException(MultipleAttemptsPresentException exc) {
        return new ResponseEntity<>("MultipleAttempts Already Present,Result Type Should Be Supply", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BatchDoesNotExistException.class)
    public ResponseEntity<String> BatchDoesNotExistException(BatchDoesNotExistException exc) {
        return new ResponseEntity<>("NO Such Batch Exists "+exc.getString(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NoCourseNameAndRegulationException.class)
    public ResponseEntity<String> NoCourseNameAndRegulationException(NoCourseNameAndRegulationException exc) {
        return new ResponseEntity<>("no entry exists with this course Name "+ exc.getString() +" and regulation "+exc.getString1(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyCourseNameExistForGivenBatchException.class)
    public ResponseEntity<String> AlreadyCourseNameExistForGivenBatchException(AlreadyCourseNameExistForGivenBatchException exc) {
        return new ResponseEntity<>("Already table entry exists with this batch "+ exc.getString1() +" and course Name "+exc.getString2(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchCourseNameExistForGivenBatchException.class)
    public ResponseEntity<String> NoSuchCourseNameExistForGivenBatchException(NoSuchCourseNameExistForGivenBatchException exc) {
        return new ResponseEntity<>("no such table entry exists with this batch "+ exc.getString1() +" and course Name "+exc.getString2(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(wrongColumnNameException.class)
    public ResponseEntity<String> wrongColumnNameException(wrongColumnNameException exc) {
        return new ResponseEntity<>(exc.getColumn_name()+" is the wrong column name in the sheet header , you shoulg use "+exc.getCorrect_column_name() , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(wrongNameException.class)
    public ResponseEntity<String> wrongNameException(wrongNameException exc) {
        return new ResponseEntity<>(exc.getColumn_name()+" is the wrong  name in the COs column  , you shoulg use "+exc.getCorrect_column_name() , HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BatchReportException.class)
    public ResponseEntity<String> wrongNameException(BatchReportException exc) {
        return new ResponseEntity<>(exc.getString()+" batch is not a complited one " , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SGPAandCGPAcolumnNameErrorInExcelException.class)
    public ResponseEntity<String> SGPAandCGPAcolumnNameErrorInExcelException() {
        return new ResponseEntity<>(" SGPA or CGPA column in the excel has given wrong name  ", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(StudentFromOtherBatchExistsException.class)
    public ResponseEntity<String> StudentFromOtherBatchExistsException() {
        return new ResponseEntity<>("In the excel sheet students results of other batches exists , please do appropriate checks before uploading regular results ", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AlreadySemesterResultsExistForGivenBatchException.class)
    public ResponseEntity<String> AlreadySemesterResultsExistForGivenBatchException(AlreadySemesterResultsExistForGivenBatchException exc) {
        return new ResponseEntity<>("semester results already exists ", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotAllStudentsresultsExistsInExcelException.class)
    public ResponseEntity<String> NotAllStudentsresultsExistsInExcelException() {
        return new ResponseEntity<>("Not all students results are given, pls Do check2 before uploading ", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DeadlineErrorException.class)
    public ResponseEntity<String> DeadlineErrorException() {
        return new ResponseEntity<>("Ask admin for date extention ", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(noDataAvailableException.class)
    public ResponseEntity<String> noDataAvailableException() {
        return new ResponseEntity<>("no data available. ", HttpStatus.BAD_REQUEST);
    }
}

