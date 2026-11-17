package com.example.cart;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    @GetMapping
    public Map<String, Object> getCart() {
        return Map.of(
            "service", "Cart Service",
            "items", List.of(
                Map.of("product", "Mobile", "quantity", 1, "price", 20000),
                Map.of("product", "Headphones", "quantity", 1, "price", 2000)
            ),
            "totalAmount", 22000
        );
    }

    @PostMapping
    public Map<String, String> addToCart() {
        return Map.of("message", "Product added to cart successfully");
    }
}
