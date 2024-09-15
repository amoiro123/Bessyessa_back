package com.bessy.productservice.dto;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
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
    @JsonIncludeProperties({"reference", "id"})
    private List<ProductDTO> products;
    @JsonIncludeProperties({"name", "id"})
    private BrandDTO brand;
}