package com.bessy.productservice.service;

import com.bessy.productservice.model.Brand;
import com.bessy.productservice.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    public Optional<Brand> findById(UUID id) {
        return brandRepository.findById(id);
    }

    public Brand save(Brand brand) {
        return brandRepository.save(brand);
    }

    public void deleteById(UUID id) {
        brandRepository.deleteById(id);
    }
}
