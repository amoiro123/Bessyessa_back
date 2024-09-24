package com.bessy.productservice.dto;

import com.bessy.productservice.enums.PriceCurrency;
import lombok.Data;
import java.util.UUID;

@Data
public class PriceDTO {
    private UUID id;
    private double amount;
    private PriceCurrency currency;
    private ProductDTO product;
}
