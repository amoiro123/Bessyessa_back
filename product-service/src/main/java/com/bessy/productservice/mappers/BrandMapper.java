package com.bessy.productservice.mappers;

import com.bessy.productservice.dto.BrandDTO;
import com.bessy.productservice.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BrandMapper {
    BrandMapper INSTANCE = Mappers.getMapper(BrandMapper.class);

    BrandDTO toDto(Brand brand);
    Brand toEntity(BrandDTO brandDTO);
}