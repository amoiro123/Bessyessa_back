package com.bessy.productservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductModelItemDTO {
    private String id;
    private String name;
    private String brandName;
    private String type;
    private boolean hasAvailable;
    private Long availableCount;
    private Long totalCount;
    private Double minPrice;
    private Double maxPrice;
}
