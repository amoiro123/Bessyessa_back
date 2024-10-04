package com.bessy.productservice.service;

import com.bessy.productservice.dto.ProductDTO;
import com.bessy.productservice.dto.ProductFilterDTO;
import com.bessy.productservice.model.Product;
import com.bessy.productservice.repository.FilterRepository;
import com.bessy.productservice.specification.ProductSpecification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilterService {
    private final FilterRepository filterRepository;
    private final ObjectMapper objectMapper;

    public List<ProductDTO> getFilteredProducts(ProductFilterDTO filterDTO) throws JsonProcessingException {
        try {
            Specification<Product> specification = ProductSpecification.filterProducts(filterDTO);
            return filterRepository.findAll(specification)
                    .stream()
                    .map(s -> objectMapper.convertValue(s, ProductDTO.class))
                    .toList();
        } catch (Exception e){
            log.error("Error getFilteredProducts filterDTO:  {}", objectMapper.writeValueAsString(filterDTO));
            throw new RuntimeException(e);
        }
    }
}
