package com.bessy.productservice.repository;

import com.bessy.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface FilterRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
}
