package com.bessy.productservice.controller;

import com.bessy.productservice.dto.ProductDTO;
import com.bessy.productservice.jwt.JwtUtil;
import com.bessy.productservice.mappers.ProductMapper;
import com.bessy.productservice.model.Product;
import com.bessy.productservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.findAll().stream().map(ProductMapper.INSTANCE::toDto).toList();
    }

    @GetMapping("/available")
    public List<ProductDTO> getAvailable() {
        return productService.findAvailables(true).stream().map(ProductMapper.INSTANCE::toDto).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/loaned")
    public List<ProductDTO> getLoaned() {
        return productService.findAvailables(false).stream().map(ProductMapper.INSTANCE::toDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable UUID id) {
        Optional<Product> product = productService.findById(id);
        return product.map(ProductMapper.INSTANCE::toDto).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO dto) {
        try {
            dto.setPublishedBy(JwtUtil.getCurrentUserID());
            dto.setAvailable(true);
            Product savedProduct = productService.save(ProductMapper.INSTANCE.toEntity(dto));
            return ResponseEntity.status(HttpStatus.CREATED).body(ProductMapper.INSTANCE.toDto(savedProduct));
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
        Product updatedProduct = productService.save(ProductMapper.INSTANCE.toEntity(dto));
        return ResponseEntity.ok(ProductMapper.INSTANCE.toDto(updatedProduct));
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
