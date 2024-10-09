package com.bessy.productservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductItemDTO {
    private UUID id;
    private String reference;

    @JsonIncludeProperties({"amount", "currency"})
    private PriceDTO currentPrice;

    @JsonIncludeProperties({"id", "loanedFrom", "loanedUntil"})
    private List<ReservationDTO> reservations = new ArrayList<>();
}
