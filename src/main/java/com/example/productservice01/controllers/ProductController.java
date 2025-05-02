package com.example.productservice01.controllers;

import com.example.productservice01.ProductService01Application;
import com.example.productservice01.dtos.FakeStoreProductDto;
import com.example.productservice01.dtos.GenericProductExceptionDto;
import com.example.productservice01.dtos.ProductNotFoundExceptionDto;
import com.example.productservice01.exceptions.GenericProductException;
import com.example.productservice01.exceptions.ProductNotFoundException;
import com.example.productservice01.models.Category;
import com.example.productservice01.models.Product;
import com.example.productservice01.services.ProductService;
import com.example.productservice01.services.ProductServiceByFakeStore;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    // private final PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer;
    // This class will handle HTTP requests related to products
    // For example, it can have methods to create, read, update, and delete products
    ProductService productService;

    public ProductController(ProductService productService, PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer) {
        // Initialize the ProductService
        this.productService = productService;
        // this.propertySourcesPlaceholderConfigurer = propertySourcesPlaceholderConfigurer;
    }

    // Example method to get all products
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductByID(@PathVariable("id") Long id) throws ProductNotFoundException {
        // Logic to retrieve all products from the database
//        return productService.getProduct(id);

        Product product = productService.getProductById(id);

//        if (product == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }

        ResponseEntity<Product> responseEntity =  new ResponseEntity<>(product, HttpStatus.OK);

        return responseEntity;
//        return ResponseEntity.ok(productService.getProduct(id));
    }

    @GetMapping()
    public List<Product> getAllProducts() {
        // Logic to retrieve all products from the database
        return productService.getAllProducts();
    }

    // Example method to get products by category
    @GetMapping("/productsByCategory/{categoryTitle}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable("categoryTitle") String categoryTitle) throws ProductNotFoundException, GenericProductException {
        // Logic to retrieve products by category from the database

        List<Product> productList = productService.getProductsByCategory(categoryTitle);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/productsByTitle/{title}")
    public ResponseEntity<List<Product>> getProductsByTitleContaining(@PathVariable("title") String title) throws ProductNotFoundException {
        // Logic to retrieve products by title from the database
        List<Product> productList = productService.getProductsByTitleContaining(title);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProductById(@PathVariable("id") Long id, @RequestBody Product product) throws ProductNotFoundException {

        // Logic to update an existing product in the database
        Product updatedProduct = productService.updateProductById(id, product);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    // Example method to create a new product
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) throws GenericProductException {
        Product createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        // Logic to create a new product in the database
    }

    // Method to delete a product
    @PutMapping("/delete/{id}")
    public void deleteProductById(@PathVariable("id") Long id) throws ProductNotFoundException {
        productService.deleteProductById(id);
    }




    // this is like a catch (global exception handler) for all the methods, not just put
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ProductNotFoundExceptionDto> handleProductNotFoundException(ProductNotFoundException productNotFoundException) {
        // Handle the exception and return a response

        ProductNotFoundExceptionDto productNotFoundExceptionDto = new ProductNotFoundExceptionDto();
        productNotFoundExceptionDto.setErrorCode(productNotFoundException.getErrorCode());
        productNotFoundExceptionDto.setMessage(productNotFoundException.getMessage());

        return new ResponseEntity<>(productNotFoundExceptionDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GenericProductException.class)
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<GenericProductExceptionDto> handleGenericProductException(GenericProductException genericProductException) {
        // Handle the exception and return a response
        GenericProductExceptionDto genericProductExceptionDto = new GenericProductExceptionDto();
        genericProductExceptionDto.setErrorCode(genericProductException.getErrorCode());
        genericProductExceptionDto.setMessage(genericProductException.getMessage());

        return new ResponseEntity<>(genericProductExceptionDto, HttpStatus.FOUND);
    }
}
