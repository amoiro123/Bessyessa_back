package com.bessy.productservice.service;

import com.bessy.productservice.enums.ProductType;
import com.bessy.productservice.model.Product;
import com.bessy.productservice.repository.ProductRepository;
import com.bessy.productservice.request.ProductRequestDTO;
import com.bessy.productservice.response.ProductResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        Product product = new Product();
        product.setName(productRequestDTO.getName());
        product.setType(productRequestDTO.getType());
        product.setAvailable(productRequestDTO.isAvailable());
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<ProductResponseDTO> getProductById(Long id) {
        return productRepository.findById(id).map(this::convertToDTO);
    }

 /*   public List<ProductResponseDTO> getProductsByType(String type) {
        List<Product> products = productRepository.findByType(String.valueOf(ProductType.valueOf(type)));
        return products.stream().map(this::convertToDTO).collect(Collectors.toList());
    }*/

    public List<ProductResponseDTO> getProductsByType(String type) {
        ProductType productType;
        try {
            productType = ProductType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid product type: " + type);
        }

        List<Product> products = productRepository.findByType(productType.valueOf(type));
        return products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductResponseDTO> getAvailableProducts() {
        List<Product> products = productRepository.findByAvailable(true);
        return products.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ProductResponseDTO updateProductAvailability(Long id, boolean availability) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setAvailable(availability);
            Product updatedProduct = productRepository.save(product);
            return convertToDTO(updatedProduct);
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    private ProductResponseDTO convertToDTO(Product product) {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(product.getId());
        productResponseDTO.setName(product.getName());
        productResponseDTO.setType(product.getType());
        productResponseDTO.setAvailable(product.isAvailable());
        return productResponseDTO;
    }
}
