package com.company;

import org.apache.poi.xssf.usermodel.XSSFRow;

public class Row {

    private final int LAST_NAME = 0;
    private final int FIRST_NAME = 1;
    private final int PATRONYMIC = 2;
    private final int ID = 3;
    private final int COURSE_NAME = 4;
    private final int RESULT = 5;
    private final int GROUP = 6;

    private final String lastName;
    private final String firstName;
    private final String patronymic;
    private final int id;
    private final String courseName;
    private final boolean isPass;
    private final String groupName;


    public Row(XSSFRow row) {
        lastName = row.getCell(LAST_NAME).getStringCellValue().trim();
        firstName = row.getCell(FIRST_NAME).getStringCellValue().trim();
        patronymic = row.getCell(PATRONYMIC).getStringCellValue().trim();
        id = Integer.parseInt(row.getCell(ID).getStringCellValue().trim());
        courseName = row.getCell(COURSE_NAME).getStringCellValue().trim();
        isPass = row.getCell(RESULT).getStringCellValue().trim().equals("Завершенo");
        groupName = row.getCell(GROUP).getStringCellValue().trim();
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public int getId() {
        return id;
    }

    public String getCourseName() {
        return courseName;
    }

    public boolean isPass() {
        return isPass;
    }

    public String getGroupName() {
        return groupName;
    }

    @Override
    public String toString() {
        return "Row{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", id=" + id +
                ", courseName='" + courseName + '\'' +
                ", isPass=" + isPass +
                ", rcName='" + groupName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Row)) return false;

        Row row = (Row) o;

        if (id != row.id) return false;
        if (isPass != row.isPass) return false;
        if (!lastName.equals(row.lastName)) return false;
        if (!firstName.equals(row.firstName)) return false;
        if (patronymic != null ? !patronymic.equals(row.patronymic) : row.patronymic != null) return false;
        if (!courseName.equals(row.courseName)) return false;
        return groupName.equals(row.groupName);
    }

    @Override
    public int hashCode() {
        int result = lastName.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + (patronymic != null ? patronymic.hashCode() : 0);
        result = 31 * result + id;
        result = 31 * result + courseName.hashCode();
        result = 31 * result + (isPass ? 1 : 0);
        result = 31 * result + groupName.hashCode();
        return result;
    }
}
