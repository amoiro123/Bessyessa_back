package com.bessy.productservice.controller;

import com.bessy.productservice.dto.ProductDTO;
import com.bessy.productservice.dto.ProductFilterDTO;
import com.bessy.productservice.exc.GenericErrorResponse;
import com.bessy.productservice.service.FilterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/product/filter")
@RequiredArgsConstructor
@Slf4j
public class FilterController {
    private final FilterService filterService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> filteredProducts(@RequestBody ProductFilterDTO filterDTO) {
        try {
            return ResponseEntity.ok().body(filterService.getFilteredProducts(filterDTO));
        } catch (Exception e) {
            log.error("Error getFilteredProducts Exception:  {}", e.getMessage());
            throw GenericErrorResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(String.format("Filtering Error: %s", e.getMessage()))
                    .build();
        }
    }
}
