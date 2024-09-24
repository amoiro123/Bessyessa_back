package com.bessy.productservice.service;

import com.bessy.productservice.model.Price;
import com.bessy.productservice.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public class PriceService {

    private final PriceRepository priceRepository;

    @Autowired
    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    // Create or Update a Price
    public Price savePrice(Price price) {
        return priceRepository.save(price);
    }

    // Get a Price by ID
    public Price getPriceById(UUID id) {
        return priceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Price not found with id " + id));
    }

    // Get all Prices
    public List<Price> getAllPrices() {
        return priceRepository.findAll();
    }

    // Delete a Price by ID
    public void deletePrice(UUID id) {
        Price price = priceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Price not found with id " + id));
        priceRepository.delete(price);
    }
}
