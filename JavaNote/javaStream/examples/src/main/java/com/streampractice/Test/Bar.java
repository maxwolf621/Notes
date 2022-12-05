package com.streampractice.Test;

import java.util.HashMap;
import java.util.HashSet;

public class Bar extends Foo{
    private int a = 10;
    private HashSet<String> hm;

    public Bar(HashSet<String> hm){
        super();
        this.hm = hm;    
    }

    public void add(){
        a += 10;
    }

    public void getHashSet(){
        hm.stream().forEach(
            e-> System.out.println(e)
        );
    }

    public void addSetElement(String a){
        this.hm.add(a);
    }
}
