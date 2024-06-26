package com.bessy.productservice.request;

import com.bessy.productservice.enums.ProductType;
import lombok.*;

import javax.persistence.Entity;

@Data
public class ProductRequestDTO {

    private String name;
    private ProductType type;
    private boolean available;

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
