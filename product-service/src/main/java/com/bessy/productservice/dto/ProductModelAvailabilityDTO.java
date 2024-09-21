package com.bessy.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductModelAvailabilityDTO {
    public String id;
    public Long availableCount;
    public Long totalCount;
}
