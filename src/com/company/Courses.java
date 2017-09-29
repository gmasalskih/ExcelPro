package com.company;

import java.util.*;

public class Courses {

    public static Set<Course> courseList = new HashSet<>();

    public static Map<String, Float> getAverageResCourse(Set<Course> courseList) {
        Map<String, Float> resultMap = new HashMap<>();
        Set<String> courseName = new HashSet<>();
        courseList.stream().forEach(course -> courseName.add(course.getName()));
        courseName.stream()
                .map(_name -> {
                    float all = courseList.stream()
                            .filter(course -> course.getName().equals(_name))
                            .count();
                    float pass = courseList.stream()
                            .filter(course -> course.getName().equals(_name))
                            .filter(course -> course.isSuccess())
                            .count();
                    float res = pass / all;
                    Map<String, Float> map = new HashMap<>();
                    map.put(_name, res);
                    return map;
                })
                .forEach(map -> resultMap.putAll(map));
        return resultMap;
    }

}
