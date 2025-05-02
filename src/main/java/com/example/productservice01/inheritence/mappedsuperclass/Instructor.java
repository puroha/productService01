package com.example.productservice01.inheritence.mappedsuperclass;
import jakarta.persistence.Entity;

@Entity(name = "jt_instructor")
public class Instructor extends User {
    private String spcialization;
    private Long salary;
}
