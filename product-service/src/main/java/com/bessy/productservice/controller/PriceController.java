package com.bessy.productservice.controller;

import com.bessy.productservice.dto.PriceDTO;
import com.bessy.productservice.mappers.PriceMapper;
import com.bessy.productservice.model.Price;
import com.bessy.productservice.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/product/prices")
@RequiredArgsConstructor
public class PriceController {
    private final PriceService priceService;

    // Create or Update a Price
    @PostMapping
    public ResponseEntity<PriceDTO> createOrUpdatePrice(@RequestBody PriceDTO dto) {
        Price savedPrice = priceService.savePrice(PriceMapper.INSTANCE.toEntity(dto));

        return new ResponseEntity<>(PriceMapper.INSTANCE.toDto(savedPrice), HttpStatus.CREATED);
    }

    // Get a Price by ID
    @GetMapping("/{id}")
    public ResponseEntity<PriceDTO> getPriceById(@PathVariable UUID id) {
        Price price = priceService.getPriceById(id);
        return new ResponseEntity<>(PriceMapper.INSTANCE.toDto(price), HttpStatus.OK);
    }

    // Get all Prices
    @GetMapping
    public ResponseEntity<List<PriceDTO>> getAllPrices() {
        List<Price> prices = priceService.getAllPrices();
        return new ResponseEntity<>(prices.stream().map(PriceMapper.INSTANCE::toDto).toList(), HttpStatus.OK);
    }

    // Delete a Price by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrice(@PathVariable UUID id) {
        priceService.deletePrice(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
