package com.streampractice;

import lombok.*;

@Data
@AllArgsConstructor
public class Student {
    private int id;
    private String name;
    private int age;
    private String sex;
    private float score;
}
