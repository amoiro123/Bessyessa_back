package com.bessy.productservice.repository;

import com.bessy.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByIsAvailable(boolean isAvailable);
    Optional<Product> findByIdAndIsAvailable(UUID id, boolean isAvailable);

    List<Product> findByProductModelId(UUID id);
}
