package com.bessy.productservice.dto;

import com.bessy.productservice.enums.ProductType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Data;

import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductModelDTO {
    private UUID id;
    private String name;
    private String description;
    private UUID addedBy;
    private String addedOn;
    @JsonIncludeProperties({"name", "id"})
    private BrandDTO brand;
    private ProductType productType;
}