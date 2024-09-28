package com.bessy.productservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Data;

import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDTO {
    private UUID id;
    private String reference;
    private UUID publishedBy;
    private UUID loanedBy;
    private boolean isAvailable;
    private String publishedOn;

    @JsonIncludeProperties({"name", "id"})
    private ProductModelDTO productModel;

    @JsonIncludeProperties({"amount", "currency"})
    private PriceDTO currentPrice;
}