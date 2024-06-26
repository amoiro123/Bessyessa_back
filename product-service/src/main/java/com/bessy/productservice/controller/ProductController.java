package com.bessy.productservice.controller;

import com.bessy.productservice.request.ProductRequestDTO;
import com.bessy.productservice.response.ProductResponseDTO;
import com.bessy.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/save")
    public ProductResponseDTO createProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        return productService.createProduct(productRequestDTO);
    }

    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Optional<ProductResponseDTO> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/type/{type}")
    public List<ProductResponseDTO> getProductsByType(@PathVariable String type) {
        return productService.getProductsByType(type);
    }

    @GetMapping("/available")
    public List<ProductResponseDTO> getAvailableProducts() {
        return productService.getAvailableProducts();
    }

    @PutMapping("/{id}/availability")
    public ProductResponseDTO updateProductAvailability(@PathVariable Long id, @RequestParam boolean availability) {
        return productService.updateProductAvailability(id, availability);
    }
}
