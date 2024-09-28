package com.bessy.productservice.controller;

import com.bessy.productservice.dto.BrandDTO;
import com.bessy.productservice.dto.ProductDTO;
import com.bessy.productservice.jwt.JwtUtil;
import com.bessy.productservice.mappers.BrandMapper;
import com.bessy.productservice.model.Brand;
import com.bessy.productservice.service.BrandService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/v1/product/brands")
@RequiredArgsConstructor
@Slf4j
public class BrandController {
    private final BrandService brandService;
    private final ObjectMapper objectMapper;

    @GetMapping
    public List<BrandDTO> getAllBrands() {
        return brandService.findAll().stream().map(p -> objectMapper.convertValue(p, BrandDTO.class)).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandDTO> getBrandById(@PathVariable UUID id) {
        Optional<Brand> brand = brandService.findById(id);
        return brand.map(p -> objectMapper.convertValue(p, BrandDTO.class)).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BrandDTO> createBrand(@RequestBody BrandDTO dto) {
        dto.setAddedBy(JwtUtil.getCurrentUserID());
        Brand savedBrand = brandService.save(objectMapper.convertValue(dto, Brand.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(objectMapper.convertValue(savedBrand, BrandDTO.class));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BrandDTO> updateBrand(@PathVariable UUID id, @RequestBody BrandDTO dto) {
        if (brandService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Brand updatedBrand = brandService.save(objectMapper.convertValue(dto, Brand.class));
        return ResponseEntity.ok(objectMapper.convertValue(updatedBrand, BrandDTO.class));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBrand(@PathVariable UUID id) {
        if (brandService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        brandService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
