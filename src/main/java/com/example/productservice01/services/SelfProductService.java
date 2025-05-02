package com.example.productservice01.services;

import com.example.productservice01.exceptions.GenericProductException;
import com.example.productservice01.exceptions.ProductNotFoundException;
import com.example.productservice01.models.Category;
import com.example.productservice01.models.Product;
import com.example.productservice01.repos.CategoryRepository;
import com.example.productservice01.repos.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class SelfProductService implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product not found with Id: " + id,
                        1005
                ));
    }

    @Override
    @Transactional
    public Product createProduct (Product product) throws GenericProductException {
        // first check if product with same title exists
        if (productRepository.existsByTitle(product.getTitle())) {
            throw new GenericProductException("Product with title " + product.getTitle() + " already exists", 1001);
        }

        // Get category from database or create new one
        Category category = categoryRepository.findByTitle(product.getCategory().getTitle())
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setTitle(product.getCategory().getTitle());
                    return categoryRepository.save(newCategory);
                });

        // Create new product with the found/created category
        Product newProduct = new Product();
        newProduct.setTitle(product.getTitle());
        newProduct.setDescription(product.getDescription());
        newProduct.setPrice(product.getPrice());
        newProduct.setCategory(category);

        // Save and return the new product
        return productRepository.save(newProduct);

    }

    @Override
    public void deleteProductById(Long id) throws ProductNotFoundException {
        // Check if product with the given ID exists
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product not found with Id: " + id,
                        1005
                ));

        // Delete the product
        productRepository.delete(existingProduct);

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public Product updateProductById (Long id, Product product) throws ProductNotFoundException {
        // Check if product with the given ID exists
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product not found with Id: " + id,
                        1005
                ));

        // Update the product details
        existingProduct.setTitle(product.getTitle());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());

        // Update the category if it has changed
        if (!existingProduct.getCategory().getTitle().equals(product.getCategory().getTitle())) {
            Category category = categoryRepository.findByTitle(product.getCategory().getTitle())
                    .orElseGet(() -> {
                        Category newCategory = new Category();
                        newCategory.setTitle(product.getCategory().getTitle());
                        return categoryRepository.save(newCategory);
                    });
            existingProduct.setCategory(category);
        }

        // Save and return the updated product
        return productRepository.save(existingProduct);
    }

    @Override
    public List<Product> getProductsByCategory(String category) throws ProductNotFoundException, GenericProductException {

        System.out.println("Checking category... " + category);
        Long count = categoryRepository.existCategoryByTitle(category);
        System.out.println("Count found for category " + count);

        if (categoryRepository.existCategoryByTitle(category) == 0) {
            throw new GenericProductException(
                    "Category not found with title: " + category,
                    1008
            );
        }

        System.out.println("Reached here, Category exists: " + category);
        List<Product> products = productRepository.findByCategoryTitle(category);

        if (products.isEmpty()) {
            throw new ProductNotFoundException(
                    "No products found in category: " + category,
                    1006
            );
        }
        return products;
    }

    @Override
    public List<Product> getProductsByTitleContaining(String title) throws ProductNotFoundException {

        List<Product> products = productRepository.findByTitleContaining(title);
        if (products.isEmpty()) {
            throw new ProductNotFoundException(
                    "No products found containing title: " + title,
                    1007
            );
        }

        return products;
    }
}
