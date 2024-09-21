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
    public Long availableCount;
    public Long totalCount;
}
