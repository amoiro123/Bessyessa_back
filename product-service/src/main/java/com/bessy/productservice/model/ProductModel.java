package com.bessy.productservice.model;

import com.bessy.productservice.enums.ProductType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

//    @JsonManagedReference // Manage the serialization of the products list
//    @OneToMany(mappedBy = "productModel", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
//    private List<Product> products;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    @JsonBackReference // Break the recursion by marking this as the back reference
    private Brand brand;

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @PrePersist
    public void setAddedOn() {
        this.addedOn = LocalDateTime.now();
    }
}
