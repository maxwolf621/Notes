package com.streampractice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;

public class streamTest {
    List<Student> students = Arrays.asList(
            new Student(1, "a", 18, "female", 88),
            new Student(2, "b", 17, "male", 60),
            new Student(3, "c", 19, "male", 100),
            new Student(4, "d", 20, "male", 10),
            new Student(5, "e", 14, "female", 100),
            new Student(6, "f", 21, "male", 55));
    StudentOps studentOps = new StudentOps();

    @Test
    public void testFilter() {
        studentOps.filterByAge(students, 20);
        studentOps.filterByScore(students);
    }

    @Test
    public void testSkipAndLimit() {
        studentOps.skipAndLimit(students, 1, 4);
    }

    @Test
    public void testSumming() {
        studentOps.summingStudentsAge(students);
    }

    @Test
    public void testGroup() {
        studentOps.groupinByAge(students);
    }

    @Test
    public void testMatch() {
        studentOps.matchOps(students, 88);
    }

    @Test
    public void testStreamOf() {
        Stream<Student> stdStream = Stream.of(
                new Student(1, "a", 18, "male", 88),
                new Student(1, "a", 18, "male", 88));
        stdStream.forEach(System.out::println);
    }

    @Test
    public void testJoin() {
        String a = Stream.of(3, 2, 3).map(String::valueOf).collect(
                Collectors.joining(",", "prefix ", " suffix"));
    }

    @Test
    public void testReduce() {
        List<Integer> reducingList = Arrays.asList(1, 2, 3, 4, 5);
        ReduceOps reduceOps = new ReduceOps();
        reduceOps.reduce(reducingList);
        reduceOps.reduceWithInitializer(reducingList, 0);
        reduceOps.reduceMultipleThreadsWithInitializer(reducingList, 0);
        reduceOps.reduceSequentialWithInitialize(reducingList, 0);
    }

    @Test
    public void testmapToObj() {
        IntStream.range(1, 4) // IntStream
                .mapToObj(i -> "a" + i) // Stream<String>
                .forEach(System.out::print); // a1a2a3
    }

    @Test
    public void testFilterWithString() {

        List<String> s = Arrays.asList(
                new String("d2"),
                new String("d3"),
                new String("b1"),
                new String("b3"),
                new String("c"));

        s.stream().filter(
                (a) -> a.equals("b3")).forEach(System.out::println);

        // if filter with { ... } then you must write if & else
        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter((o) -> {
                    // o : string type
                    if (o.equals("b3")) {
                        return true;
                    } else
                        return false;
                }).forEach(a -> System.out.println(a.getClass().getName()));

                Stream<Student> stdStream = Stream.of(
                    new Student(1, "a", 19, "male", 88), 
                    new Student(2, "a", 18, "female", 90));
                List<Student> stds = stdStream.peek(o -> o.setAge(100)).collect(Collectors.toList());
                System.out.println(stds.toString());
            }


            @Test public void testInstream(){
                IntStream.rangeClosed(-150 , 0).forEach(
                    i ->{
                        Integer a = i;
                        Integer b = i;
                        System.out.println(i + " " + (a == b));
                    }
                );
            }

}

