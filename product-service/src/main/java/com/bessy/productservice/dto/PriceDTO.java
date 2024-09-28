package com.bessy.productservice.dto;

import com.bessy.productservice.enums.PriceCurrency;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Data;
import java.util.UUID;

@Data
public class PriceDTO {
    private UUID id;
    private double amount;
    private PriceCurrency currency;
    @JsonIncludeProperties({"id", "reference"})
    private ProductDTO product;

    private UUID addedBy;

    private String addedOn;
}
