package com.bessy.productservice.service;

import com.bessy.productservice.dto.ProductModelAvailabilityDTO;
import com.bessy.productservice.model.ProductModel;
import com.bessy.productservice.repository.ProductModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
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

    public ProductModel update(ProductModel existing, ProductModel updated) {
        if(Objects.nonNull(updated.getName()))
            existing.setName(updated.getName());

        if(Objects.nonNull(updated.getDescription()))
            existing.setDescription(updated.getDescription());

        return productModelRepository.save(existing);
    }

    public void deleteById(UUID id) {
        productModelRepository.deleteById(id);
    }

    public List<ProductModelAvailabilityDTO> findProductModelAvailability(){
        List<Object[]> result = productModelRepository.findProductModelAvailability();
        return result.stream()
                .map(res -> new ProductModelAvailabilityDTO(res[0].toString(), (Long) res[1], (Long) res[2], (Double) res[3], (Double) res[4])).toList();
    }
}
