package com.streampractice;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    @Test
    public void streamTest(){
       Stream<String> s = IntStream.range(1, 4) // IntStream
                .mapToObj(i -> "a" + i); // Stream<String>
        s.forEach(System.out::println);

        assertTrue(true);
    }

    @Test
    public void intStreamParallel(){
        IntStream.rangeClosed(1, 5).parallel().forEach(
            System.out::println
        );
        assertTrue(true);
    }

    @Test
    public void retainAllList(){
        List<Integer> l = new ArrayList<>(Arrays.asList(1,2,3,4,5,6));
        List<Integer> l2 = new ArrayList<>(Arrays.asList(1,2,3,4,7,8));
        l.retainAll(l2);
        l.forEach(System.out::println);
    }

    @Test
    public void stringBuilderTest(){
        StringBuilder str = new StringBuilder("GONE WRONG_01");
        str.setCharAt( 0 , 'a' );
        str.setLength(15);
        str.chars().forEach(System.out::println);

        System.out.println(str.length());

        char[] array = new char[10];
        Arrays.fill(array, '_');
  
        // get char from index 5 to 9
        // and store in array start index 3
        str.getChars(5, 10, array, 3);

        Stream.of(array).forEach(System.out::print);
    }

    @Test
    public void stringTrim(){
        String s1 = " Learn Share Learn ";
        String s2 = s1.trim(); // returns "Learn Share Learn"
        System.out.println(s1);
        System.out.println(s2);
    }

    @Test public void entrySet(){
        Map<Integer, String> sites = Map.of(
            1,"Google",
            2,"Youtube",
            3,"Github"
        );
        sites.entrySet().forEach(System.out::println);
    }   
}
