package com.bessy.productservice.controller;

import com.bessy.productservice.dto.ItemDTO;
import com.bessy.productservice.dto.ProductModelDTO;
import com.bessy.productservice.dto.ProductModelItemDTO;
import com.bessy.productservice.enums.ProductType;
import com.bessy.productservice.jwt.JwtUtil;
import com.bessy.productservice.mappers.DropdownItemMapper;
import com.bessy.productservice.mappers.ProductModelItemMapper;
import com.bessy.productservice.model.ProductModel;
import com.bessy.productservice.service.ProductModelService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper;

    @GetMapping
    public List<ProductModelItemDTO> getAllProductModels() {
        return ProductModelItemMapper.productModelItemDTOList(productModelService.findAll(), productModelService.findProductModelAvailability());
    }

    @GetMapping("/items")
    public List<ItemDTO> getProductModelsDropdown() {
        return productModelService.findAll().stream().map(DropdownItemMapper::mapToItemDTO).toList();
    }

    @GetMapping("/categories")
    public List<ProductType> getProductModelCategories() {
        return List.of(ProductType.values());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductModelDTO> getProductModelById(@PathVariable String id) {
        Optional<ProductModel> productModel = productModelService.findById(UUID.fromString(id));
        return productModel.map(p -> objectMapper.convertValue(p, ProductModelDTO.class)).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductModelDTO> createProductModel(@RequestBody ProductModelDTO dto) {
        dto.setAddedBy(JwtUtil.getCurrentUserID());
        ProductModel savedProductModel = productModelService.save(objectMapper.convertValue(dto, ProductModel.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(objectMapper.convertValue(savedProductModel, ProductModelDTO.class));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductModelDTO> updateProductModel(@PathVariable UUID id, @RequestBody ProductModelDTO dto) {
        Optional<ProductModel> existing = productModelService.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ProductModel updatedProductModel = productModelService.update(existing.get(), objectMapper.convertValue(dto, ProductModel.class));
        return ResponseEntity.ok(objectMapper.convertValue(updatedProductModel, ProductModelDTO.class));
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
