package com.bessy.productservice.controller;

import com.bessy.productservice.dto.ProductModelDTO;
import com.bessy.productservice.dto.ProductModelItemDTO;
import com.bessy.productservice.jwt.JwtUtil;
import com.bessy.productservice.mappers.ProductModelItemMapper;
import com.bessy.productservice.mappers.ProductModelMapper;
import com.bessy.productservice.model.ProductModel;
import com.bessy.productservice.service.ProductModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/v1/product/models")
@RequiredArgsConstructor
public class ProductModelController {
    private final ProductModelService productModelService;

    @GetMapping
    public List<ProductModelItemDTO> getAllProductModels() {
        return ProductModelItemMapper.productModelItemDTOList(productModelService.findAll(), productModelService.findProductModelAvailability());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductModelDTO> getProductModelById(@PathVariable String id) {
        Optional<ProductModel> productModel = productModelService.findById(UUID.fromString(id));
        return productModel.map(ProductModelMapper.INSTANCE::toDto).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductModelDTO> createProductModel(@RequestBody ProductModelDTO dto) {
        dto.setAddedBy(JwtUtil.getCurrentUserID());
        ProductModel savedProductModel = productModelService.save(ProductModelMapper.INSTANCE.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductModelMapper.INSTANCE.toDto(savedProductModel));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductModelDTO> updateProductModel(@PathVariable UUID id, @RequestBody ProductModelDTO dto) {
        if (productModelService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ProductModel updatedProductModel = productModelService.save(ProductModelMapper.INSTANCE.toEntity(dto));
        return ResponseEntity.ok(ProductModelMapper.INSTANCE.toDto(updatedProductModel));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProductModel(@PathVariable UUID id) {
        if (productModelService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        productModelService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
