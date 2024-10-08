package com.bessy.productservice.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
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

    private LocalDateTime loanedOn;

    @ManyToOne
    @JoinColumn(name = "product_model_id")
    private ProductModel productModel;

    @PrePersist
    public void setPublishedOn() {
        this.publishedOn = LocalDateTime.now();
    }
}
