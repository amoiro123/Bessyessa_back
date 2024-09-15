package com.bessy.productservice.model;

import com.bessy.productservice.enums.ProductType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
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

    @JsonIncludeProperties({"name", "id"})
    @OneToMany(mappedBy = "brand", cascade = { CascadeType.MERGE, CascadeType.REMOVE })
    private List<ProductModel> productModel;

    private UUID addedBy;  // User who added the product model

    @Enumerated(EnumType.STRING)
    private ProductType productType;
}
