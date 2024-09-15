package com.bessy.productservice.dto;

import com.bessy.productservice.enums.ProductType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class BrandDTO {
    private UUID id;
    private String name;
    private String description;
    @JsonIgnoreProperties({"brand"})
    private List<ProductModelDTO> productModel;
    private UUID addedBy;
    private ProductType productType;
}