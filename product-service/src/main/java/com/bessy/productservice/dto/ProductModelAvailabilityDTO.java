package com.bessy.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
public class ProductModelAvailabilityDTO {
    private String id;
    private Long availableCount;
    private Long totalCount;
    private Double minPrice;
    private Double maxPrice;
}
