package com.example.productservice01.models;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Category extends BaseModel {
//    private Long id;
//    private String title;


    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();


    public Category(Long id, String title) {
        super(id, title);
    }
    // Default constructor for JPA
    public Category() {
    }

    public Long getId() {
        return super.getId();
    }
    public void setId(Long id) {
        super.setId(id);
    }

    public String getTitle() {
        return super.getTitle();
    }

    public void setTitle(String title) {
        super.setTitle(title);
    }
}
