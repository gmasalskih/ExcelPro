package com.company;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Excel {

    private final int LAST_NAME = 0;
    private final int FIRST_NAME = 1;
    private final int PATRONYMIC = 2;
    private final int ID = 3;
    private final int COURSE_NAME = 4;
    private final int RESULT = 5;
    private final int RC = 6;

    private static Excel instance;
    private XSSFSheet sheet;
    private Set<Employee> employeeList;
    private Set<RC> rcSet;
    private List<XSSFRow> rows = new ArrayList<>();

    private Excel() {

        Arrays.asList((new File("./")).listFiles()).stream()
                .filter(file -> file.getAbsolutePath().contains(".xlsx"))
                .map(file -> getSheet(file))
                .forEach(sheet -> sheet.rowIterator().forEachRemaining(row -> {
                    if (row.getRowNum() > 1) rows.add((XSSFRow) row);
                }));

        rows.forEach(row -> {
            Courses.courseList
                    .add(new Course(row.getCell(COURSE_NAME).getStringCellValue(),
                            row.getCell(RESULT).getStringCellValue(),
                            row.getCell(RC).getStringCellValue().trim(),
                            row.getCell(ID).getStringCellValue()));
        });
        employeeList = initEmployee(rows);
        rcSet = initRC(rows);
    }

    public static Excel newInstance() throws IOException {
        if (instance == null) instance = new Excel();
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

    private Set<Employee> initEmployee(List<XSSFRow> rows) {
        Set<Employee> employeeSet = rows.stream()
                .map(row -> new Employee(
                        row.getCell(ID).getStringCellValue(),
                        row.getCell(FIRST_NAME).getStringCellValue(),
                        row.getCell(LAST_NAME).getStringCellValue(),
                        row.getCell(PATRONYMIC).getStringCellValue(),
                        row.getCell(RC).getStringCellValue()))
                .collect(Collectors.toSet());
        employeeSet.forEach(employee -> {

        });
        return employeeSet;
    }

    private Set<RC> initRC(List<XSSFRow> rows) {
        Set<RC> rcSet = rows.stream()
                .map(row -> new RC(row.getCell(RC).getStringCellValue()))
                .collect(Collectors.toSet());
        rcSet.forEach(rc -> employeeList.forEach(rc::addEmployee));
        return rcSet;
    }

    public Map<String, Float> getAverageRC() {
        return rcSet.stream().collect(Collectors.toMap(k -> k.getName(), v -> v.averageRC()));
    }

    public Map<String, Map<String, Float>> getResultsOfCourseRC() {
        Map<String, Map<String, Float>> map = new HashMap<>();
        rcSet.forEach(rc -> {
            Map<String, Float> _map = new HashMap<>();
            _map.putAll(rc.getAverageResCourse());
            map.put(rc.getName(), _map);
        });
        System.out.println(map);
        return map;
    }

    public Set<com.company.RC> getRcSet() {
        return rcSet;
    }

    public Map<String, Float> getAverageCourses() {
        return Courses.getAverageResCourse(Courses.courseList);
    }

    public int getAmountOfCourses() {
        return new HashSet<Course>(Courses.courseList).size();
    }

    public int amountRC() {
        return rcSet.size();
    }

    public int amountEmployee() {
        return employeeList.size();
    }
}























