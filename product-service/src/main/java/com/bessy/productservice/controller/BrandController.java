package com.bessy.productservice.controller;

import com.bessy.productservice.dto.BrandDTO;
import com.bessy.productservice.mappers.BrandMapper;
import com.bessy.productservice.model.Brand;
import com.bessy.productservice.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    public List<BrandDTO> getAllBrands() {
        return brandService.findAll().stream().map(BrandMapper.INSTANCE::toDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandDTO> getBrandById(@PathVariable UUID id) {
        Optional<Brand> brand = brandService.findById(id);
        return brand.map(BrandMapper.INSTANCE::toDto).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BrandDTO> createBrand(@RequestBody Brand brand) {
        Brand savedBrand = brandService.save(brand);
        return ResponseEntity.status(HttpStatus.CREATED).body(BrandMapper.INSTANCE.toDto(savedBrand));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrandDTO> updateBrand(@PathVariable UUID id, @RequestBody Brand brand) {
        if (brandService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        brand.setId(id);
        Brand updatedBrand = brandService.save(brand);
        return ResponseEntity.ok(BrandMapper.INSTANCE.toDto(updatedBrand));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable UUID id) {
        if (brandService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        brandService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
