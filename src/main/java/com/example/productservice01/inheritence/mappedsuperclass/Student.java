package com.example.productservice01.inheritence.mappedsuperclass;

import jakarta.persistence.Entity;

@Entity(name = "jt_student")
public class Student extends User {
    private int batchID;
    private String major;
}
