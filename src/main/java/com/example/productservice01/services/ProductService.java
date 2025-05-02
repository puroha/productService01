package com.example.productservice01.services;

import com.example.productservice01.exceptions.GenericProductException;
import com.example.productservice01.exceptions.ProductNotFoundException;
import com.example.productservice01.models.Product;
import org.springframework.stereotype.Service;

import javax.management.InstanceNotFoundException;
import java.util.List;

@Service
public interface ProductService {
    // This interface will define the methods that the ProductService class must implement
    // For example, it can have methods to create, read, update, and delete products

    // Example method to get all products
    Product getProductById (Long id) throws ProductNotFoundException;

    // Example method to create a new product
    Product createProduct (Product product) throws GenericProductException;

    // Example method to delete a product
    void deleteProductById(Long Id) throws ProductNotFoundException ;

    List<Product> getAllProducts();

    Product updateProductById (Long id, Product product) throws ProductNotFoundException;

    List<Product> getProductsByCategory(String category) throws ProductNotFoundException, GenericProductException ;
    List<Product> getProductsByTitleContaining(String title) throws ProductNotFoundException;
}
