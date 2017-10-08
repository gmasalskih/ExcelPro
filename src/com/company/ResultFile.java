package com.company;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class ResultFile {

    private static ResultFile instance;
    private Excel excel;
    private XSSFWorkbook wbOut = new XSSFWorkbook();
    private XSSFSheet sheetOut;
    private final static String OUT_FILE_NAME = "./Result.xlsx";

    private ResultFile(Excel excel) {
        (new File(OUT_FILE_NAME)).delete();
        this.excel = excel;
    }

    public static ResultFile newInstance(Excel excel) {
        if (instance == null) instance = new ResultFile(excel);
        return instance;
    }

    private void resultsOfCourses() {
        sheetOut = wbOut.createSheet("resultsOfCourses");
        excel.getResultsOfCourses().forEach((courseName, result) -> {
            XSSFRow row = sheetOut.createRow(sheetOut.getLastRowNum() + 1);
            row.createCell(0).setCellValue(courseName);
            row.createCell(1).setCellType(CellType.NUMERIC);
            row.getCell(1).setCellValue(result);
        });
    }

    private void resultsOfGroups() {
        sheetOut = wbOut.createSheet("resultsOfGroups");
        excel.getResultsOfGroups().forEach((groupName, result) -> {
            XSSFRow row = sheetOut.createRow(sheetOut.getLastRowNum() + 1);
            row.createCell(0).setCellValue(groupName);
            row.createCell(1).setCellType(CellType.NUMERIC);
            row.getCell(1).setCellValue(result);
        });
    }

    private void resultsOfGroupsByCourses() {
        sheetOut = wbOut.createSheet("resultsOfGroupsByCourses");
        excel.getResultsOfGroupsByCourses().forEach((groupName, coursesMap) -> {
            coursesMap.forEach((courseName, result) -> {
                XSSFRow row = sheetOut.createRow(sheetOut.getLastRowNum() + 1);
                row.createCell(0).setCellValue(groupName);
                row.createCell(1).setCellValue(courseName);
                row.createCell(2).setCellType(CellType.NUMERIC);
                row.getCell(2).setCellValue(result);
            });
        });
    }

    private void resultsOfCoursesByGroups() {
        sheetOut = wbOut.createSheet("resultsOfCoursesByGroups");
        excel.getResultsOfCoursesByGroups().forEach((courseName, groupsMap) -> {
            groupsMap.forEach((groupName, result) -> {
                XSSFRow row = sheetOut.createRow(sheetOut.getLastRowNum() + 1);
                row.createCell(0).setCellValue(courseName);
                row.createCell(1).setCellValue(groupName);
                row.createCell(2).setCellType(CellType.NUMERIC);
                row.getCell(2).setCellValue(result);
            });
        });
    }


    private void amountSuccessEmployeesInGroups() {
        sheetOut = wbOut.createSheet("amountSuccessEmployeesInGroups");
        excel.getAmountEmployeesInGroups().forEach((groupName, amountAll) -> {
            excel.getAmountSuccessEmployeesInGroups().forEach((_groupName, amountSuccess) -> {
                if (groupName.equals(_groupName)) {
                    XSSFRow row = sheetOut.createRow(sheetOut.getLastRowNum() + 1);
                    row.createCell(0).setCellValue(groupName);
                    row.createCell(1).setCellType(CellType.NUMERIC);
                    row.getCell(1).setCellValue(amountAll);
                    row.createCell(2).setCellType(CellType.NUMERIC);
                    row.getCell(2).setCellValue(amountSuccess);
                    row.createCell(3).setCellType(CellType.NUMERIC);
                    row.getCell(3).setCellValue((float) amountSuccess / (float) amountAll);
                }
            });
        });
    }

    public void writeFile() {
        resultsOfCourses();
        resultsOfGroups();
        amountSuccessEmployeesInGroups();
        resultsOfGroupsByCourses();
        resultsOfCoursesByGroups();
        try (FileOutputStream fos = new FileOutputStream(OUT_FILE_NAME)) {
            wbOut.write(fos);
            wbOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
