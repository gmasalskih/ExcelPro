package com.company;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class FileMaker {

    private static FileMaker instance;
    private List<XSSFRow> rows = new ArrayList<>();
    private final String[] arrRC;

    private FileMaker(String[] arrRC) {
        (new File("./all.xlsx")).delete();
        (new File("./Result.xlsx")).delete();
        this.arrRC = arrRC;
        Arrays.asList((new File("./")).listFiles()).stream()
                .filter(file -> file.getAbsolutePath().contains(".xlsx"))
                .map(file -> getSheet(file))
                .forEach(sheet -> sheet.rowIterator().forEachRemaining(row -> {
                    if (row.getRowNum() > 1) rows.add((XSSFRow) row);
                }));
    }

    public static FileMaker newInstance(String[] rc) {
        if (instance == null) instance = new FileMaker(rc);
        return instance;
    }

    private XSSFSheet getSheet(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            return new XSSFWorkbook(fis).getSheetAt(0);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void makeFile() {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Sheet");
        for (int i = 0; i < rows.size(); i++) {
            XSSFRow row = sheet.createRow(i);
            for (int index = 0; index < rows.get(i).getLastCellNum(); index++) {
                if (index == 6) {
                    row.createCell(index).setCellValue(checkRC(rows.get(i).getCell(index).getStringCellValue()));
                    continue;
                }
                row.createCell(index).setCellValue(rows.get(i).getCell(index).getStringCellValue());
            }
        }
        try (FileOutputStream fos = new FileOutputStream("./all.xlsx")) {
            wb.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String checkRC(String groupName) {
        for (String rc : arrRC) {
            if (groupName.contains(rc)) return rc.trim();
        }
        return groupName;
    }

}
