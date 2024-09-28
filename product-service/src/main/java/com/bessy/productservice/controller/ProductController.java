package com.bessy.productservice.controller;

import com.bessy.productservice.dto.ProductDTO;
import com.bessy.productservice.jwt.JwtUtil;
import com.bessy.productservice.mappers.ProductMapper;
import com.bessy.productservice.model.Product;
import com.bessy.productservice.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/product")
@Slf4j
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ObjectMapper objectMapper;

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.findAll().stream().map(p -> objectMapper.convertValue(p, ProductDTO.class)).toList();
    }

    @GetMapping("/available")
    public List<ProductDTO> getAvailable() {
        return productService.findAvailables(true).stream().map(p -> objectMapper.convertValue(p, ProductDTO.class)).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/loaned")
    public List<ProductDTO> getLoaned() {
        return productService.findAvailables(false).stream().map(p -> objectMapper.convertValue(p, ProductDTO.class)).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable UUID id) {
        Optional<Product> product = productService.findById(id);
        return product.map(p -> objectMapper.convertValue(p, ProductDTO.class)).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO dto) {
        try {
            dto.setPublishedBy(JwtUtil.getCurrentUserID());
            dto.setAvailable(true);
            Product savedProduct = productService.save(objectMapper.convertValue(dto, Product.class));
            return ResponseEntity.status(HttpStatus.CREATED).body(objectMapper.convertValue(savedProduct, ProductDTO.class));
        } catch (Exception e){
            log.error("createProduct - Reference: {} - Error: {} ", dto.getReference(), e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable UUID id, @RequestBody ProductDTO dto) {
        if (productService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Product updatedProduct = productService.save(objectMapper.convertValue(dto, Product.class));
        return ResponseEntity.ok(objectMapper.convertValue(updatedProduct, ProductDTO.class));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        if (productService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
