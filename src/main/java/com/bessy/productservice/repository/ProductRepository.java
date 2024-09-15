package com.bessy.productservice.repository;

import com.bessy.productservice.enums.ProductType;
import com.bessy.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
}