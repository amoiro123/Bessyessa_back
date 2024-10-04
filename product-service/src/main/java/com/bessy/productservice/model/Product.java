package com.bessy.productservice.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Product implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String reference;

    private UUID publishedBy;  // Admin user

    private UUID loanedBy;  // Client user, nullable since it may not be loaned yet

    private boolean isAvailable;

    @Column(nullable = false)
    private LocalDateTime publishedOn;

    @ManyToOne
    @JoinColumn(name = "product_model_id", referencedColumnName = "id")
    @JsonIncludeProperties({"name", "id", "brand"})
    private ProductModel productModel;

    @OneToOne(cascade = { CascadeType.MERGE, CascadeType.REMOVE })
    @JoinColumn(name = "current_price_id", referencedColumnName = "id")
    @JsonIncludeProperties({"amount", "currency"})
    private Price currentPrice;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIncludeProperties({"amount", "currency", "addedOn"})
    private List<Price> previousPrices = new ArrayList<>();


    @OneToMany(mappedBy = "product", cascade = {}, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIncludeProperties({"id", "loanedOn", "userId", "loanedFrom", "loanedUntil"})
    private List<Reservation> reservations = new ArrayList<>();

    @PrePersist
    public void setPublishedOn() {
        this.publishedOn = LocalDateTime.now();
    }
}
