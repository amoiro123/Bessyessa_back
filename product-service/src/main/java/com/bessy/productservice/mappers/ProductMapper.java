package com.bessy.productservice.mappers;

import com.bessy.productservice.dto.ProductDTO;
import com.bessy.productservice.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper extends MapperUtil {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "publishedOn", target = "publishedOn", qualifiedByName = "localDateTimeToString")
    ProductDTO toDto(Product product);

    @Mapping(source = "publishedOn", target = "publishedOn", qualifiedByName = "stringToLocalDateTime")
    Product toEntity(ProductDTO productDTO);
}