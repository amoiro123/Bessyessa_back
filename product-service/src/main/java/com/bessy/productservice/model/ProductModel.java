package com.bessy.productservice.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class ProductModel implements Serializable {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private UUID addedBy;  // User who added the product model

    @Column(nullable = false)
    private LocalDateTime addedOn;

    @JsonIncludeProperties({"reference", "id"})
    @OneToMany(mappedBy = "productModel", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<Product> products;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    @JsonIncludeProperties({"name", "id"})
    private Brand brand;

    // Getters, Setters, etc.

    @PrePersist
    public void setAddedOn() {
        this.addedOn = LocalDateTime.now();
    }
}
