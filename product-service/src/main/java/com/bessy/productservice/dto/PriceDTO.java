package com.bessy.productservice.dto;

import com.bessy.productservice.enums.PriceCurrency;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Data;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PriceDTO {
    private UUID id;
    private double amount;
    private PriceCurrency currency;
    @JsonIncludeProperties({"id", "reference"})
    private ProductDTO product;

    private UUID addedBy;

    private String addedOn;
}
