package com.streampractice;

import org.junit.Test;

public class testString {
    @Test public void split(){
        String a = "IN.MA.CA.LA";
        String[] res = a.split("\\.", 2);
        for(int i = 0; i < res.length ; i++){
            System.out.println(res[i]);
        }
    }
}
