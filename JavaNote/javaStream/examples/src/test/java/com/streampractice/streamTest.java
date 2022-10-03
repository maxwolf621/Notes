package com.streampractice;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class streamTest {
    List<Student> students = Arrays.asList(
        new Student(1, "a", 18, "female", 88),
        new Student(2, "b", 17, "male", 60),
        new Student(3, "c", 19, "male", 100),
        new Student(4, "d", 20, "male", 10),
        new Student(5, "e", 14, "female",100),
        new Student(6, "f", 21, "male", 55)
    );
    StudentOps studentOps = new StudentOps();

    @Test public void testFilter(){
        studentOps.filterByAge(students, 20);
        studentOps.filterByScore(students);
    }

    @Test public void testSkipAndLimit(){
        studentOps.skipAndLimit(students, 1, 4);
    }

    @Test public void testSumming(){
        studentOps.summingStudentsAge(students);
    }
    @Test public void testGroup(){
        studentOps.groupinByAge(students);
    }
    @Test public void testMatch(){
        studentOps.matchOps(students, 88);
    }

    @Test public void testStreamOf(){
        Stream<Student> stdStream = Stream.of(
            new Student(1, "a", 18, "male", 88), 
            new Student(1, "a", 18, "male", 88));
        stdStream.forEach(System.out::println);
    }
    @Test public void testJoin(){
        String a = Stream.of(3, 2, 3).map(String::valueOf).collect(
            Collectors.joining("," , "prefix ", " suffix"));
    }

    @Test public void testReduce(){
        List<Integer> reducingList = Arrays.asList(1, 2, 3, 4, 5);
        ReduceOps reduceOps = new ReduceOps();
        reduceOps.reduce(reducingList);
        reduceOps.reduceWithInitializer(reducingList, 0);
        reduceOps.reduceMultipleThreadsWithInitializer(reducingList, 0);
        reduceOps.reduceSequentialWithInitialize(reducingList, 0);
    }





}
