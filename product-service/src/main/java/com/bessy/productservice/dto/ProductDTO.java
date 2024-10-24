package com.bessy.productservice.dto;

import com.bessy.productservice.model.Price;
import com.bessy.productservice.model.Reservation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    @JsonIncludeProperties({"amount", "currency", "addedOn"})
    private List<PriceDTO> previousPrices = new ArrayList<>();

    @JsonIncludeProperties({"id", "loanedOn", "userId", "loanedFrom", "loanedUntil"})
    private List<ReservationDTO> reservations = new ArrayList<>();
}