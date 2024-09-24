package com.bessy.productservice.model;

import com.bessy.productservice.enums.PriceCurrency;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
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
}
