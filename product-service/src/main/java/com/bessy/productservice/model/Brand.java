package com.bessy.productservice.model;

import com.bessy.productservice.enums.ProductType;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Brand implements Serializable {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private List<ProductModel> productModel;

    private UUID addedBy;  // User who added the product model

    @Enumerated(EnumType.STRING)
    private ProductType productType;
}
