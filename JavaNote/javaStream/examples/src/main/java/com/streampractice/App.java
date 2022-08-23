package com.streampractice;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;
public class App 
{
    public static void main( String[] args )
    {
   
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "a", 18, "male", 88));
        students.add(new Student(2, "b", 17, "male", 60));
        students.add(new Student(3, "c", 19, "male", 100));
        students.add(new Student(4, "d", 20, "male", 10));
        students.add(new Student(5, "e", 14, "female", 95));
        students.add(new Student(6, "f", 21, "male", 55));
        
        StudentOps studentOps = new StudentOps();
        studentOps.filterByAge(students);
        studentOps.filterByScore(students);
        studentOps.summingStudentsAge(students);
        studentOps.groupinByAge(students);
        studentOps.matchOps(students, 88);
        studentOps.skipAndLimit(students, 1, 4);

        //Stream.of(students).flatMap(e).forEach(System.out.println);
        //Stream<Student> stdStream = Stream.of(new Student(1, "a", 18, "male", 88), new Student(1, "a", 18, "male", 88));
        //stdStream.forEach(System.out::println);
        // Stream<Integer> stream2 = Stream.iterate(0, (x) -> x + 3).limit(5);
        // stream2.forEach(System.out::println);
        //Stream<Double> stream3 = Stream.generate(Math::random).limit(2);
        //String a= Stream.of(3, 2, 3).map(String::valueOf).collect(Collectors.joining("," , "prefix ", " suffix"));
        //System.out.println(a);
        

        List<Integer> reducingList = Arrays.asList(1, 2, 3, 4, 5);
        ReduceOps reduceOps = new ReduceOps();
        reduceOps.reduce(reducingList);
        reduceOps.reduceWithInitializer(reducingList, 0);
        reduceOps.reduceMultipleThreadsWithInitializer(reducingList, 0);
        reduceOps.reduceSequentialWithInitialize(reducingList, 0);

        List<Fruit> fruitList = Arrays.asList(
            new Fruit("apple", 6),
            new Fruit("apple", 6),
            new Fruit("banana", 7), 
            new Fruit("banana", 7),
            new Fruit("banana", 7), 
            new Fruit("grape",8)
        );

        FruitOps fruitOps = new FruitOps();
        fruitOps.groupingByFruitName(fruitList);
        fruitOps.groupingByFruitNameAndMapping(fruitList);
        fruitOps.groupingByFruitNameWithCounting(fruitList);

        Stream.of("a1", "a2", "a3")
                .map(s -> s.substring(1)) // "1", "2", "3"
                .mapToInt(Integer::parseInt) // 1,2,3
                .max() // 3
                .ifPresent(e -> System.out.println("max value : " + e));

        /**
         * Anonymous Class
         * Method Reference Static
         * Method Reference Instance
         */
        List<String> strList = Arrays.asList("A","B","C");
        // using Anonymous class
        strList.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println("using anonymous class " + s); // ABC
            }
        });
        // Method reference STATIC (CLASS_NAME::METHOD) 
        strList.forEach(App::staticPrint); // ABC
        // Method reference INSTANCE (INSTANCE::METHOD)
        App main = new App();
        strList.forEach(main::instancePrint); // ABC



        Arrays.asList("a1", "a2", "b1", "c2", "c1")
                .parallelStream()
                .filter(s -> {
                    System.out.format("filter: %s [%s]\n",
                        s, Thread.currentThread().getName());
                    return true;
                })
                .map(s -> {
                    System.out.format("map: %s [%s]\n",
                        s, Thread.currentThread().getName());
                    return s.toUpperCase();
                })
                .sorted((s1, s2) -> {
                    System.out.format("sort: %s <> %s [%s]\n",
                        s1, s2, Thread.currentThread().getName());
                    return s1.compareTo(s2);
                })
                .forEach(s -> System.out.format("forEach: %s [%s]\n",
                    s, Thread.currentThread().getName()));
    }
    private static void staticPrint(String s) {
        System.out.println("Static Method " + s);
    }
    private void instancePrint(String s) {
        System.out.println("instance method " + s);
    }
}
