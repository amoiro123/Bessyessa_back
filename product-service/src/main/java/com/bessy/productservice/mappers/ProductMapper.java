package com.bessy.productservice.mappers;

import com.bessy.productservice.dto.ProductDTO;
import com.bessy.productservice.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDTO toDto(Product product);
    Product toEntity(ProductDTO productDTO);
}