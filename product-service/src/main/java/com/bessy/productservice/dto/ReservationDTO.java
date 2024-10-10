package com.bessy.productservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Data;

import java.util.UUID;

@Data
public class ReservationDTO {
    private UUID id;
    private String loanedOn;
    private String loanedFrom;
    private String loanedUntil;
    private UUID userId;

    @JsonIncludeProperties({"id", "amount", "currency"})
    private PriceDTO price;
    @JsonIncludeProperties({"reference", "id", "productModel"})
    private ProductDTO product;
}
