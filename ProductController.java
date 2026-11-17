package com.example.product;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Value("${server.port}")
    private String port;

    @GetMapping
    public Map<String, Object> getProducts() {
        return Map.of(
            "service", "Product Service",
            "instancePort", port,
            "products", List.of(
                Map.of("id", 1, "name", "Laptop", "price", 55000),
                Map.of("id", 2, "name", "Mobile", "price", 20000),
                Map.of("id", 3, "name", "Headphones", "price", 2000)
            )
        );
    }

    @GetMapping("/{id}")
    public Map<String, Object> getProduct(@PathVariable int id) {
        return Map.of(
            "service", "Product Service",
            "instancePort", port,
            "productId", id
        );
    }
}
