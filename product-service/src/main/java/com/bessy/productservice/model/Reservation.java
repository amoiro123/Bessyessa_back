package com.bessy.productservice.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Reservation implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private LocalDateTime loanedOn;

    @Column(nullable = false)
    private LocalDateTime loanedFrom;

    @Column(nullable = false)
    private LocalDateTime loanedUntil;

    private UUID userId;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.DETACH })
    @JoinColumn(name = "price_id", referencedColumnName = "id")
    @JsonIncludeProperties({ "id", "amount", "currency"})
    private Price price;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.DETACH })
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @JsonIncludeProperties({"reference", "id", "productModel"})
    private Product product;
}
