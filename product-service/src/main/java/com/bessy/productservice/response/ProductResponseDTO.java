package com.bessy.productservice.response;

import com.bessy.productservice.enums.ProductType;
import lombok.Data;

@Data
public class ProductResponseDTO {

    private Long id;
    private String name;
    private ProductType type;
    private boolean available;
    private String description;
    private String imageId;
}