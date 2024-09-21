package com.bessy.productservice.mappers;

import com.bessy.productservice.dto.ProductModelAvailabilityDTO;
import com.bessy.productservice.dto.ProductModelItemDTO;
import com.bessy.productservice.model.ProductModel;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ProductModelItemMapper {
    public static List<ProductModelItemDTO> productModelItemDTOList(List<ProductModel> models, List<ProductModelAvailabilityDTO> availabilityList) {
        return models.stream().map(model -> {
            Optional<ProductModelAvailabilityDTO> op = availabilityList.stream().filter(a -> Objects.equals(a.getId(), model.getId().toString())).findFirst();
            if(op.isEmpty())
                return productModelItemDTO(model, 0L, 0L);
            ProductModelAvailabilityDTO availability = op.get();
           return productModelItemDTO(model, availability.getAvailableCount(), availability.getTotalCount());
        }).toList();
    }

    private static ProductModelItemDTO productModelItemDTO(ProductModel model, Long availableCount, Long totalCount) {
        return ProductModelItemDTO.builder()
                .id(model.getId().toString())
                .name(model.getName())
                .brandName(Objects.nonNull(model.getBrand()) ? model.getBrand().getName() : "")
                .type(model.getProductType().toString().toLowerCase())
                .hasAvailable(availableCount > 0)
                .availableCount(availableCount)
                .totalCount(totalCount)
                .build();
    }
}
