package com.example.productservice01.repos;

import com.example.productservice01.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT COUNT(*) > 0 FROM category WHERE LOWER(title) = LOWER(:categoryTitle)", nativeQuery = true)
    Long existCategoryByTitle(String categoryTitle);

    @Query(value = "SELECT * FROM category WHERE LOWER(category.title) = LOWER(:title)", nativeQuery = true)
    Optional<Category> findByTitle(String title);
    // JpaRepository provides methods for CRUD operations
    // No need to implement any methods here
}
