package com.streampractice;

import java.util.*;

import com.streampractice.C.C3;
import com.streampractice.Test.Bar;
import com.streampractice.Test.Foo;
public class App 
{
    // private static final int Student = 0;
    
    String title = "";
    int value;

    public App() {
      this.title = "world";
    }
    public App(int value) {
      this.value = value;
      this.title = "Hello";
    }
    public static void main( String[] args )
    {
        // App c = new App(1);
        // System.out.println(c.title);

        Foo bar = new Bar();
        System.out.println((Bar) bar.a);

        
        /* 
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
        */
        /**
         * Anonymous Class
         * Method Reference Static
         * Method Reference Instance
        List<String> strList = Arrays.asList("A","B","C");
        // using Anonymous class
        strList.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println("using anonymous class " + s); // ABC
            }
        });
        private static void staticPrint(String s) {
        System.out.println("Static Method " + s);
        }
        private void instancePrint(String s) {
            System.out.println("instance method " + s);
        }
        */

        /**
         * Method reference STATIC (CLASS_NAME::METHOD) 
        strList.forEach(App::staticPrint); // ABC
        */

        /** 
         * Method reference INSTANCE (INSTANCE::METHOD)
        App main = new App();
        strList.forEach(main::instancePrint); // ABC
        */


        /**
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
                    s, Thread.currentThread().getName()));*/

                

    }
}