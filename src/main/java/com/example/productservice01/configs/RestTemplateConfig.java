package com.example.productservice01.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    // Please add @Bean annotation to the method that creates a RestTemplate bean
    // This method will create and return a RestTemplate bean
    // The RestTemplate is used to make HTTP requests to external APIs
    // The @Configuration annotation indicates that this class contains one or more @Bean methods
    // The @Bean annotation indicates that a method produces a bean to be managed by the Spring container
    // The RestTemplate bean can be injected into other components in the application
    // The RestTemplate is a synchronous client to perform HTTP requests
    // The RestTemplate can be used to consume RESTful web services
    @Bean
    // adding BEAN MEANS THIS METHOD WILL CREATE A BEAN WHICH WILL BE MANAGED BY SPRING
    // The @Bean annotation indicates that a method produces a bean to be managed by the Spring container
    // The RestTemplate bean can be injected into other components in the application
    public RestTemplate restTemplate() {
        // This method will create and return a RestTemplate bean
        // The RestTemplate is used to make HTTP requests to external APIs
        return new RestTemplate();
    }
}
