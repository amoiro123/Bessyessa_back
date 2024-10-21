package com.bessy.productservice.service;

import com.bessy.productservice.client.FileStorageClient;
import com.bessy.productservice.enums.ProductType;
import com.bessy.productservice.exc.GenericErrorResponse;
import com.bessy.productservice.exc.NotAvailableException;
import com.bessy.productservice.jwt.JwtUtil;
import com.bessy.productservice.model.Product;
import com.bessy.productservice.repository.ProductRepository;
import com.bessy.productservice.request.ProductRequestDTO;
import com.bessy.productservice.response.ProductResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import java.util.UUID;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }
    public List<Product> findAvailables(boolean available) {
        return productRepository.findByIsAvailable(available);
    }

    public Optional<Product> findById(UUID id) {
        return productRepository.findById(id);
    }

    public List<Product> findByProductModelIdId(UUID id) {
        return productRepository.findByProductModelId(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(UUID id) {
        productRepository.deleteById(id);
    }

    public void loan(String id){
        try {
            UUID uuid = UUID.fromString(id);
            Optional<Product> productOptional = productRepository.findByIdAndIsAvailable(uuid, true);
            if(productOptional.isEmpty())
                throw new NotAvailableException(id);

            Product product = productOptional.get();
            product.setLoanedBy(JwtUtil.getCurrentUserID());
            product.setAvailable(false);
            productRepository.save(product);
        } catch (Exception e){
            log.error("Loan Service - UUID: {} - Error: {}", id, e.getMessage());
            throw new GenericErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
