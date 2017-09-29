package com.company;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class ResultFile {

    private static ResultFile instance;
    private Excel excel;
    private XSSFWorkbook wbOut = new XSSFWorkbook();
    private XSSFSheet sheetOut;
    private String outFileName;

    private ResultFile(Excel excel) {
        outFileName = "./Result.xlsx";
        this.excel = excel;
    }

    public static ResultFile newInstance(Excel excel) {
        if (instance == null) instance = new ResultFile(excel);
        return instance;
    }

    private void averageCourses() {
        sheetOut = wbOut.createSheet("averageCourses");
        excel.getAverageCourses().forEach((k, v) -> {
            XSSFRow row = sheetOut.createRow(sheetOut.getLastRowNum() + 1);
            row.createCell(0).setCellValue(k);
            row.createCell(1).setCellType(CellType.NUMERIC);
            row.getCell(1).setCellValue(v);
        });
    }

    private void resultsOfCourseRC() {
        sheetOut = wbOut.createSheet("resultsOfCourseRC");
        List<CourseHelper> courseHelpers = new ArrayList<>();
        Set<String> courseNameSet = new LinkedHashSet<>();
        sheetOut.createRow(1).createCell(0);
        excel.getResultsOfCourseRC().forEach((rcName, mapCourses) -> {
            sheetOut.getRow(1).createCell(sheetOut.getRow(1).getLastCellNum()).setCellValue(rcName);
            mapCourses.forEach((k, v) -> courseNameSet.add(k));
        });

        courseNameSet.forEach(name -> {
            XSSFRow row = sheetOut.createRow(sheetOut.getLastRowNum() + 1);
            row.createCell(0).setCellValue(name);
            excel.getResultsOfCourseRC().forEach((rcName, mapCourses)->{
                mapCourses.forEach((courseName, courseRes)->{
                    if(name.equals(courseName)){
                        row.createCell(row.getLastCellNum()).setCellValue(courseRes);
                    }
                });
            });
        });


//        courseHelpers.stream()
//                .forEach(courseHelper -> {
//                    XSSFRow row = sheetOut.createRow(sheetOut.getLastRowNum() + 1);
//                    row.createCell(0).setCellValue(courseHelper.getRc());
//                    row.createCell(1).setCellValue(courseHelper.getCourseName());
//                    row.createCell(2).setCellType(CellType.NUMERIC);
//                    row.getCell(2).setCellValue(courseHelper.getRes());
//                });
    }

    private void averageRC() {
        sheetOut = wbOut.createSheet("averageRC");
        excel.getAverageRC().forEach((k, v) -> {
            XSSFRow row = sheetOut.createRow(sheetOut.getLastRowNum() + 1);
            row.createCell(0).setCellValue(k);
            row.createCell(1).setCellType(CellType.NUMERIC);
            row.getCell(1).setCellValue(v);
        });
    }

    private void amountSuccessfulEmployee() {
        sheetOut = wbOut.createSheet("amountSuccessfulEmployee");
        excel.getRcSet().forEach(rc -> {
            XSSFRow row = sheetOut.createRow(sheetOut.getLastRowNum() + 1);
            row.createCell(0).setCellValue(rc.getName());
            row.createCell(1).setCellType(CellType.NUMERIC);
            row.getCell(1).setCellValue(rc.getAmountEmployee());
            row.createCell(2).setCellType(CellType.NUMERIC);
            row.getCell(2).setCellValue(rc.getAmountSuccessfulEmployee());
            row.createCell(3).setCellType(CellType.NUMERIC);
            row.getCell(3).setCellValue(((float) rc.getAmountSuccessfulEmployee()) / ((float) rc.getAmountEmployee()));
        });
    }

    public void writeFile() {
        averageRC();
        averageCourses();
        resultsOfCourseRC();
        amountSuccessfulEmployee();
        try (FileOutputStream fos = new FileOutputStream(outFileName)) {
            wbOut.write(fos);
            wbOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
