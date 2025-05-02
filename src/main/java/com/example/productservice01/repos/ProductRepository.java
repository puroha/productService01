package com.example.productservice01.repos;

import com.example.productservice01.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // JpaRepository provides methods for CRUD operations
    // No need to implement any methods here
    boolean existsByTitle(String title);
}
