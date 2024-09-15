package com.bessy.productservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private LocalDateTime publishedOn;
    private LocalDateTime loanedOn;

    @JsonIgnoreProperties({"products"})
    private ProductModelDTO productModel;
}