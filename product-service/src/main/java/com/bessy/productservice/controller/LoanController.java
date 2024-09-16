package com.bessy.productservice.controller;

import com.bessy.productservice.exc.GenericErrorResponse;

import com.bessy.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/product/loan")
@RequiredArgsConstructor
public class LoanController {
    private final ProductService productService;

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> loan(@PathVariable String id) throws GenericErrorResponse {
        productService.loan(id);
        return ResponseEntity.ok().build();
    }
}
