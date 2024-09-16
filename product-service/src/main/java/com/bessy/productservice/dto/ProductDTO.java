package com.bessy.productservice.dto;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ProductDTO {
    private UUID id;
    private String reference;
    private UUID publishedBy;
    private UUID loanedBy;
    private boolean isAvailable;
    private String publishedOn;
    private String loanedOn;

    @JsonIncludeProperties({"name", "id"})
    private ProductModelDTO productModel;
}