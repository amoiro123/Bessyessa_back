package com.bessy.productservice.controller;

import com.bessy.productservice.dto.ProductModelDTO;
import com.bessy.productservice.mappers.ProductModelMapper;
import com.bessy.productservice.model.ProductModel;
import com.bessy.productservice.service.ProductModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/product-models")
public class ProductModelController {

    @Autowired
    private ProductModelService productModelService;

    @GetMapping
    public List<ProductModelDTO> getAllProductModels() {
        return productModelService.findAll().stream().map(ProductModelMapper.INSTANCE::toDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductModelDTO> getProductModelById(@PathVariable UUID id) {
        Optional<ProductModel> productModel = productModelService.findById(id);
        return productModel.map(ProductModelMapper.INSTANCE::toDto).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProductModelDTO> createProductModel(@RequestBody ProductModel productModel) {
        ProductModel savedProductModel = productModelService.save(productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductModelMapper.INSTANCE.toDto(savedProductModel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductModelDTO> updateProductModel(@PathVariable UUID id, @RequestBody ProductModel productModel) {
        if (productModelService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        productModel.setId(id);
        ProductModel updatedProductModel = productModelService.save(productModel);
        return ResponseEntity.ok(ProductModelMapper.INSTANCE.toDto(updatedProductModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductModel(@PathVariable UUID id) {
        if (productModelService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        productModelService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
