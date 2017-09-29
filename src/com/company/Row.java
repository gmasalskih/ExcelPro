package com.company;

import org.apache.poi.xssf.usermodel.XSSFRow;

public class Row {

    private final String[] arrRC = {"МРЦ", "ПУФО", "ПФО1", "СЗФО", "СФО ", "СФО2", "УФО", "ЦФО1", "ЦФО2", "ЦФО3", "ЮФО ", "ЮФО2"};
    private final int LAST_NAME = 0;
    private final int FIRST_NAME = 1;
    private final int PATRONYMIC = 2;
    private final int ID = 3;
    private final int COURSE_NAME = 4;
    private final int RESULT = 5;
    private final int RC = 6;

    private final String lastName;
    private final String firstName;
    private final String patronymic;
    private final int id;
    private final String courseName;
    private final boolean isPass;
    private String rcName;


    public Row(XSSFRow row) {
        lastName = row.getCell(LAST_NAME).getStringCellValue().trim();
        firstName = row.getCell(LAST_NAME).getStringCellValue().trim();
        patronymic = row.getCell(LAST_NAME).getStringCellValue().trim();
        id = Integer.parseInt(row.getCell(LAST_NAME).getStringCellValue().trim());
        courseName = row.getCell(LAST_NAME).getStringCellValue().trim();
        isPass = row.getCell(LAST_NAME).getStringCellValue().trim().equals("Завершенo");

        for (int i = 0; i < arrRC.length; i++) {
            if (row.getCell(LAST_NAME).getStringCellValue().trim().equals(arrRC[i])) {
                rcName = arrRC[i].trim();
            }
        }


    }


}
