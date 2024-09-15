package com.bessy.productservice.service;

import com.bessy.productservice.model.ProductModel;
import com.bessy.productservice.repository.ProductModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductModelService {

    @Autowired
    private ProductModelRepository productModelRepository;

    public List<ProductModel> findAll() {
        return productModelRepository.findAll();
    }

    public Optional<ProductModel> findById(UUID id) {
        return productModelRepository.findById(id);
    }

    public ProductModel save(ProductModel productModel) {
        return productModelRepository.save(productModel);
    }

    public void deleteById(UUID id) {
        productModelRepository.deleteById(id);
    }
}
