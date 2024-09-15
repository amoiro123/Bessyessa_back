package com.bessy.productservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.bessy.productservice.dto.ProductModelDTO;
import com.bessy.productservice.model.ProductModel;

@Mapper
public interface ProductModelMapper {
    ProductModelMapper INSTANCE = Mappers.getMapper(ProductModelMapper.class);

    ProductModelDTO toDto(ProductModel productModel);
    ProductModel toEntity(ProductModelDTO productModelDTO);
}