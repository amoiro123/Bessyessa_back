package com.bessy.productservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class ProductModelDTO {
    private UUID id;
    private String name;
    private String description;
    private UUID
    ;
    private LocalDateTime addedOn;
    @JsonIgnoreProperties({"productModel"})
    private List<ProductDTO> products;
    @JsonIgnoreProperties({"productModel"})
    private BrandDTO brand;
}