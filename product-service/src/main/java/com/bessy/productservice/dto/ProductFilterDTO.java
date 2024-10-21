package com.bessy.productservice.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ProductFilterDTO {
    private UUID productModelId;   // Filter by product model
    private String productModelName;
    private UUID brandId;          // Filter by brand inside the product model
    private Double minPrice;  // Filter by minimum price
    private Double maxPrice;  // Filter by maximum price
    private LocalDateTime loanFrom;
    private LocalDateTime loanUntil;
}
