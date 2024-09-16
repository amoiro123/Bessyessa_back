package com.bessy.productservice.dto;

import com.bessy.productservice.enums.ProductType;

import lombok.Data;

import java.util.UUID;

@Data
public class BrandDTO {
    private UUID id;
    private String name;
    private String description;
    private UUID addedBy;
    private ProductType productType;
}