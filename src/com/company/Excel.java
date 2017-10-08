package com.company;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

public class Excel {

    private static Excel instance;
    private Map<Integer, Set<Row>> employeesMap;
    private Map<String, Set<Row>> groupsMap;
    private Map<String, Set<Row>> coursesMap;
    private Set<Row> rowsSet = new HashSet<>();

    private Excel() {
        Arrays.asList((new File("./")).listFiles()).stream()
                .filter(file -> file.getAbsolutePath().contains(".xlsx"))
                .map(file -> getSheet(file))
                .forEach(sheet -> sheet.rowIterator().forEachRemaining(row -> {
                    if (row.getRowNum() > 1) rowsSet.add(new Row((XSSFRow) row));
                }));
        employeesMap = rowsSet.stream().collect(groupingBy(Row::getId, toSet()));
        groupsMap = rowsSet.stream().collect(groupingBy(Row::getGroupName, toSet()));
        coursesMap = rowsSet.stream().collect(groupingBy(Row::getCourseName, toSet()));
    }

    public static Excel newInstance() throws IOException {
        if (instance == null) instance = new Excel();
        return instance;
    }


    // --------Groups--------

    public Map<String, Set<Row>> getGroupsMap() {
        return groupsMap;
    }

    public int getAmountGroups() {
        return groupsMap.size();
    }

    public Set<String> getGroupsName() {
        return groupsMap.keySet();
    }

    public Map<String, Float> getResultsOfGroups() {
        Map<String, Float> map = new HashMap<>();
        groupsMap.forEach((groupName, rows) -> {
            float all = (float) rows.stream().count();
            float pass = (float) rows.stream().filter(row -> row.isPass() == true).count();
            map.put(groupName, pass / all);
        });
        return map;
    }

    public Map<String, Map<String, Float>> getResultsOfGroupsByCourses() {
        Map<String, Map<String, Float>> mapGroupsOfMapsCourses = new HashMap<>();
        groupsMap.forEach((groupName, rows) -> {
            Map<String, Float> mapResultsOfCourses = new HashMap<>();
            rows.stream()
                    .collect(groupingBy(Row::getCourseName, toSet()))
                    .forEach((courseName, _rows) -> {
                        float all = (float) _rows.size();
                        float pass = (float) _rows.stream().filter(row -> row.isPass() == true).count();
                        mapResultsOfCourses.put(courseName, pass / all);
                    });
            mapGroupsOfMapsCourses.put(groupName, mapResultsOfCourses);
        });
        return mapGroupsOfMapsCourses;
    }


    // -------Courses---------

    public Map<String, Set<Row>> getCoursesMap() {
        return coursesMap;
    }

    public Set<String> getCoursesName() {
        return coursesMap.keySet();
    }

    public int getAmountCourses() {
        return coursesMap.size();
    }

    public Map<String, Float> getResultsOfCourses() {
        Map<String, Float> map = new HashMap<>();
        coursesMap.forEach((courseName, rows) -> {
            float all = (float) rows.stream().count();
            float pass = (float) rows.stream().filter(row -> row.isPass() == true).count();
            map.put(courseName, pass / all);
        });
        return map;
    }

    public Map<String, Map<String, Float>> getResultsOfCoursesByGroups() {
        Map<String, Map<String, Float>> mapCoursesOfMapsGroups = new HashMap<>();
        coursesMap.forEach((courseName, rows) -> {
            Map<String, Float> mapResultsOfGroups = new HashMap<>();
            rows.stream()
                    .collect(groupingBy(Row::getGroupName, toSet()))
                    .forEach((groupName, _rows) -> {
                        float all = (float) _rows.size();
                        float pass = (float) _rows.stream().filter(row -> row.isPass() == true).count();
                        mapResultsOfGroups.put(groupName, pass / all);
                    });
            mapCoursesOfMapsGroups.put(courseName, mapResultsOfGroups);
        });
        return mapCoursesOfMapsGroups;
    }


    // -----------Employees--------

    public Map<Integer, Set<Row>> getEmployeesMap() {
        return employeesMap;
    }

    public int getAmountEmployee() {
        return employeesMap.size();
    }

    public Map<String, Integer> getAmountEmployeesInGroups() {
        Map<String, Integer> map = new HashMap<>();
        groupsMap.forEach((groupName, rows) -> {
            map.put(groupName, (int) rows.stream().map(Row::getId).distinct().count());
        });
        return map;
    }

    public Map<String, Integer> getAmountSuccessEmployeesInGroups() {
        Map<String, Integer> map = new HashMap<>();
        groupsMap.forEach((groupName, rows) -> {
            Set<Integer> set = new HashSet<>();
            rows.stream()
                    .collect(groupingBy(Row::getId, toSet()))
                    .forEach((employeeID, _rows) -> {
                        if (_rows.stream().allMatch(row -> row.isPass() == true)) set.add(employeeID);
                    });
            map.put(groupName, set.size());
        });
        return map;
    }


    // -----------Other------------

    private XSSFSheet getSheet(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            return new XSSFWorkbook(fis).getSheetAt(0);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void showAllRow() {
        rowsSet.forEach(row -> System.out.println(row));
    }
}























