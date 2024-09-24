package com.bessy.productservice.repository;

import com.bessy.productservice.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PriceRepository extends JpaRepository<Price, UUID> {
}
