package com.bessy.productservice.controller;

import com.bessy.productservice.dto.PriceDTO;
import com.bessy.productservice.exc.GenericErrorResponse;
import com.bessy.productservice.exc.NotFoundException;
import com.bessy.productservice.jwt.JwtUtil;
import com.bessy.productservice.model.Price;
import com.bessy.productservice.model.Product;
import com.bessy.productservice.service.PriceService;
import com.bessy.productservice.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.protocol.HTTP;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/product/prices")
@RequiredArgsConstructor
@Slf4j
public class PriceController {
    private final PriceService priceService;
    private final ProductService productService;
    private final ObjectMapper objectMapper;

    // Create or Update a Price
    @PostMapping
    public ResponseEntity<PriceDTO> createOrUpdatePrice(@RequestBody PriceDTO dto) {
        if(Objects.nonNull(dto.getProduct())){
            UUID id = dto.getProduct().getId();
            Optional<Product> productOptional = productService.findById(id);
            if(productOptional.isEmpty())
                throw new NotFoundException(String.format("Product id %s not found", id));
            Product product = productOptional.get();
            dto.setAddedBy(JwtUtil.getCurrentUserID());
            Price savedPrice = priceService.savePrice(objectMapper.convertValue(dto, Price.class));
            product.setCurrentPrice(savedPrice);
            product = productService.save(product);
            savedPrice.setProduct(product);
            return new ResponseEntity<>(objectMapper.convertValue(savedPrice, PriceDTO.class), HttpStatus.CREATED);
        } else {
            throw new GenericErrorResponse("Product must not be null!", HttpStatus.BAD_REQUEST);
        }
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
