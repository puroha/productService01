package com.example.productservice01.repos;

import com.example.productservice01.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // JpaRepository provides methods for CRUD operations
    // No need to implement any methods here
    boolean existsByTitle(String title);

    @Query(value = "SELECT * FROM product WHERE product.category_id = (SELECT id FROM category WHERE LOWER(title) = LOWER(:categoryTitle))", nativeQuery = true)
    List<Product> findByCategoryTitle(String categoryTitle);

    @Query(value = "SELECT * FROM product WHERE LOWER(title) LIKE LOWER(CONCAT('%', :title, '%'))", nativeQuery = true)
    List<Product> findByTitleContaining(String title);
}
