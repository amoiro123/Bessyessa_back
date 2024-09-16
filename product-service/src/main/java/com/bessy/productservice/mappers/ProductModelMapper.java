package com.bessy.productservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import com.bessy.productservice.dto.ProductModelDTO;
import com.bessy.productservice.model.ProductModel;

@Mapper
public interface ProductModelMapper extends MapperUtil {
    ProductModelMapper INSTANCE = Mappers.getMapper(ProductModelMapper.class);

    @Mapping(source = "addedOn", target = "addedOn", qualifiedByName = "localDateTimeToString")
    ProductModelDTO toDto(ProductModel productModel);

    @Mapping(source = "addedOn", target = "addedOn", qualifiedByName = "stringToLocalDateTime")
    ProductModel toEntity(ProductModelDTO productModelDTO);
}