package com.streampractice;

import java.util.*;
import java.util.stream.Collectors;

public class StudentOps {
    
    public void groupinByAge(List<Student> students){
        System.out.println("groupinByAge");
        Map<String, Map<Integer, List<Student>>> typeAgeMap = students.stream().collect(
            Collectors.groupingBy(
                Student::getSex, 
                Collectors.groupingBy(Student::getAge)
            )
        );
        System.out.println("GroupBy : " +  typeAgeMap);
    }

    public void summingStudentsAge(List<Student> students){
        System.out.println("summingStudentsAge");
        Integer sumAge = students.stream().collect(
            Collectors.summingInt(
                Student::getAge)); 
        System.out.println("sum of age : " + sumAge);
    }

    public void filterByScore(List<Student> students){
        System.out.println("filterByScore");
        students.stream().forEach(System.out::println);
        students.stream().filter(s -> s.getScore() >= 90).collect(
            Collectors.toList()).stream().forEach(System.out::println);
    }

    public void filterByAge(List<Student> students){
        System.out.println("filterByAge");
        students.stream().filter(
            student -> student.getAge() == 20
        ).findFirst().ifPresent(System.out::println);
    }

    public void matchOps(List<Student> students, int targetScore){
        System.out.println("match Ops");
        // find any student meet the criteria
        boolean anyMatch = students.stream().anyMatch(
            student -> student.getScore() == targetScore);
        System.out.println("One of Students Matches the score " + targetScore + " " + anyMatch);
            // All Student's scores meet the criteria
        boolean allMatch = students.stream().allMatch(
            student -> student.getScore() == targetScore);
        System.out.println("All Students Matches the score " + targetScore + " " + allMatch);
        // None of Student meet the criteria 
        boolean noneMatch = students.stream().noneMatch(
            student -> student.getScore() == targetScore);
        System.out.println("No Students match the score " + targetScore + " " + noneMatch);
    }

    public void skipAndLimit(List<Student> students, int skips, int limit){
        System.out.println("skipAndLimit");
        students.stream().skip(skips).limit(limit).collect(
            Collectors.toList()).forEach(System.out::println);
        }


    public void sortStudentByScoreASC(List<Student> students){
        System.out.println("sortStudentByScoreASC");
        //sort student by score with ascending order
        students.stream().sorted(
            Comparator.comparing(Student::getScore))
            .collect(Collectors.toList())
            .forEach(System.out::println);
    }

    public void sortStudentByScoreDSC(List<Student> students){
        System.out.println("sortStudentByScoreDSC");
        // sort student by score with descending order
        students.stream().sorted(
            Comparator.comparing(Student::getScore)
            .reversed())
            .collect(Collectors.toList()).forEach(System.out::println);
    }

    public void sortStudentByScoreThenAge(List<Student> students){
        System.out.println("sortStudentByScoreThenAge");
        // sort first by score then age
        List<Student> sortByScoreAndAge = students.stream().sorted(
            Comparator.comparing(
                Student::getScore)
                .thenComparing(
                    Student::getAge))
                .collect(Collectors.toList());
        sortByScoreAndAge.forEach(System.out::println);
    }

    public void removeDuplicatesByDistinct(List<Student> students){
        System.out.println("removeDuplicatesByDistinct");
        // remove the duplicate elements
        students.stream().map(e -> e.getScore()).distinct().collect(
            Collectors.toList()).forEach(System.out::println);
        Set<Student> studentSet = students.stream().collect(Collectors.toSet()); 
        System.out.println("using via Set<Student>");
        studentSet.forEach(System.out::println);
    }
}
