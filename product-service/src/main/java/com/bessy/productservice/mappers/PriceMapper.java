package com.bessy.productservice.mappers;

import com.bessy.productservice.dto.PriceDTO;
import com.bessy.productservice.model.Price;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PriceMapper {
    PriceMapper INSTANCE = Mappers.getMapper(PriceMapper.class);

    @Mapping(target = "product", ignore = true)
    PriceDTO toDto(Price price);

    Price toEntity(PriceDTO priceDTO);
}