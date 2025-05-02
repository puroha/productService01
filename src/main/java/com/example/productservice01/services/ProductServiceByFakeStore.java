package com.example.productservice01.services;

import com.example.productservice01.dtos.FakeStoreProductDto;
import com.example.productservice01.exceptions.ProductNotFoundException;
import com.example.productservice01.models.Category;
import com.example.productservice01.models.Product;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

import javax.management.InstanceNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceByFakeStore implements ProductService {
    // This class will implement the methods defined in the ProductService interface
    // For example, it can have methods to create, read, update, and delete products
    // It can also interact with the database to perform these operations

    RestTemplate restTemplate;

    public ProductServiceByFakeStore(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        // can create a http client and create request to call the fake store API
        // Better way is to use a library like RestTemplate or WebClient


        // Logic to fetch product in Dto and convert that to Product and return.
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + id,
                FakeStoreProductDto.class
        );

        if (fakeStoreProductDto == null) {
            throw new ProductNotFoundException("Product not found with Id: " + id, 1005);
        }


        return convertFakeProductDtoToProduct(fakeStoreProductDto);
    }

    public Product convertFakeProductDtoToProduct(FakeStoreProductDto fakeStoreProductDto) {
        // Logic to convert FakeStoreProductDto to Product
        return new Product(
                fakeStoreProductDto.getId(),
                fakeStoreProductDto.getTitle(),
                fakeStoreProductDto.getDescription(),
                fakeStoreProductDto.getPrice(),
                new Category(fakeStoreProductDto.getId(), fakeStoreProductDto.getCategory())
        );
    }

    @Override
    public Product createProduct(Product product) {
        // Logic to create a new product in the database
        return null;
    }

    @Override
    public void deleteProductById(Long id) {
        // Logic to delete a product from the database
    }

    @Override
    public List<Product> getAllProducts() {
        // using an array instead of a list because of
        // Type erasure pattern in Java for List (for backwards compatibility)
        // If a list was used, it would have been a List<FakeStoreProductDto> at compile time
        // and List FakeStoreProductDto at runtime. Spring would not be able to know
        // which type to convert to.
        FakeStoreProductDto[] fakeStoreProductDtos = restTemplate.getForObject(
                "https://fakestoreapi.com/products",
                FakeStoreProductDto[].class
        );


        return Arrays.stream(fakeStoreProductDtos)
                .map(this::convertFakeProductDtoToProduct)
                .collect(Collectors.toList());
    }

    @Override
    public Product updateProductById(Long id, Product product) {

        if (product.getId() != id) {
            throw new RuntimeException("ID mismatch: " + id + " != " + product.getId());
        }

        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(id);
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setCategory(product.getCategory().getTitle());

        try {
            HttpEntity<FakeStoreProductDto> request = new HttpEntity<>(fakeStoreProductDto);
            ResponseEntity<FakeStoreProductDto> response = restTemplate.exchange(
                    "https://fakestoreapi.com/products/" + id,
                    HttpMethod.PUT,
                    request,
                    FakeStoreProductDto.class
            );

            if (response.getBody() == null) {
                throw new RuntimeException("Empty response received");
            }
            return convertFakeProductDtoToProduct(response.getBody());
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Product not found with id: " + id, e);
        } catch (ResourceAccessException e) {
            throw new RuntimeException("API timeout or connection error", e);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("API error: " + e.getStatusText(), e);
        }

    }

}
