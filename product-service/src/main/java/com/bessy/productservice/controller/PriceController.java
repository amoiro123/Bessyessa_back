package com.bessy.productservice.controller;

import com.bessy.productservice.dto.PriceDTO;
import com.bessy.productservice.model.Price;
import com.bessy.productservice.service.PriceService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper;

    // Create or Update a Price
    @PostMapping
    public ResponseEntity<PriceDTO> createOrUpdatePrice(@RequestBody PriceDTO dto) {
        Price savedPrice = priceService.savePrice(objectMapper.convertValue(dto, Price.class));
        return new ResponseEntity<>(objectMapper.convertValue(savedPrice, PriceDTO.class), HttpStatus.CREATED);
    }

    // Get a Price by ID
    @GetMapping("/{id}")
    public ResponseEntity<PriceDTO> getPriceById(@PathVariable UUID id) {
        Price price = priceService.getPriceById(id);
        return new ResponseEntity<>(objectMapper.convertValue(price, PriceDTO.class), HttpStatus.OK);
    }

    // Get all Prices
    @GetMapping
    public ResponseEntity<List<PriceDTO>> getAllPrices() {
        List<Price> prices = priceService.getAllPrices();
        return new ResponseEntity<>(prices.stream().map(price -> objectMapper.convertValue(price, PriceDTO.class)).toList(), HttpStatus.OK);
    }

    // Delete a Price by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrice(@PathVariable UUID id) {
        priceService.deletePrice(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
