package com.bessy.productservice.model;

import com.bessy.productservice.enums.PriceCurrency;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Price implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    private double amount;

    @Enumerated(EnumType.STRING)
    private PriceCurrency currency;

    private UUID addedBy;  // User who added the price

    @Column(nullable = false)
    private LocalDateTime addedOn;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    @JsonIncludeProperties({"id", "reference"})
    private Product product;
}
