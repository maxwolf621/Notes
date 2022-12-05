package com.streampractice;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

import com.streampractice.Test.Bar;

abstract class C1
{
  public C1() {
    System.out.print(1);
  }
}

class C2 extends C1
{
  public C2() {
    System.out.print(2);
  }
}

class C3 extends C2
{
  public C3() {
    System.out.print(3);
  }
}

/**
 * Unit test for simple App.
 */
public class AppTest 
{      
    
    public static void main(String[] a) {
        //new C3();
        HashSet<String> set = new HashSet<String>(){{
            add("a");
            add("b");
            add("c");
        }};
        Bar b = new Bar(set);
        Bar b2 = b;
        
        b.addSetElement("Im new value");
        b = null; // b reference to null

        b2.getHashSet();

    }


    static int proc(int i){
        try{
            if(i == 0 ){
                System.out.println("----Try Block----");  // 1
                throw new NullPointerException("demo");    // 2
            }else{
                return i;
            }
        }catch(NullPointerException e){
            System.out.println("----Catch Block----");  // 1
            System.out.println("Caught inside proc");  // 1
            throw e;
        }finally{
            System.out.println("----Finally-Block----");  // 1
            i = 7749;
        }
    }

    @Test public void testException(){
        int i = 0;
        try{
            i = proc(i);
        }catch(NullPointerException e){
            System.out.println("Re-caught: "+e);       //3 
        }
        System.out.println("Result i : "+ i);       //3 

    }


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


    @Test public void hashSetStream(){
        Set<Integer> set = new HashSet<Integer>(){{
            add(1);
            add(2);
            add(3);
        }};

        set.stream().forEach(System.out::println);

        Map<Integer, Integer> m = new HashMap<Integer,Integer>(){{
            put(1,4);
            put(2,1);
            put(3,3);
        }};

        m.forEach((k,v)->
        {
            if(k > 0){
                System.out.println(v);
            };
            if(k < 0){
                System.out.print(0);
            }
        });
    }
    @Test public void testFunction(){
        converter<String, Integer> convertToInteger = (from) -> Integer.valueOf(from);
        Integer i1=convertToInteger.convert("124");

        converter<String,Integer> toInteger = 
        new converter<String,Integer>(){
            @Override
            public Integer convert(String from) {
                return Integer.valueOf(from);
            }
        };

        Integer i2 = toInteger.convert("321");

        System.out.println(i1 + ":" + i2);
    }

    @Test public void testMethodReference(){
        class Something {
            String startsWith(String s) {
                return String.valueOf(s.charAt(0));
            }
        }
        Something something = new Something();
        converter<String, String> converter = something::startsWith;
        String converted = converter.convert("Java");
        System.out.println(converted);    // "J"
    }

}
