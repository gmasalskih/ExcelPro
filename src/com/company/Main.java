package com.company;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        (new File("./Result.xlsx")).delete();
        Excel excel = Excel.newInstance();
        System.out.println(excel.getResultsOfCoursesByGroups());
        System.out.println(excel.getResultsOfGroupsByCourses());
        System.out.println(excel.getAmountCourses());
    }
}