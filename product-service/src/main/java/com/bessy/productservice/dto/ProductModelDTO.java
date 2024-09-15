package com.bessy.productservice.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class ProductModelDTO {
    private UUID id;
    private String name;
    private String description;
    private UUID addedBy;
    private LocalDateTime addedOn;
    private List<ProductDTO> products;
    private BrandDTO brand;
}