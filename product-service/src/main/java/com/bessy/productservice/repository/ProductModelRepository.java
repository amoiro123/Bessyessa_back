package com.bessy.productservice.repository;

import com.bessy.productservice.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductModelRepository extends JpaRepository<ProductModel, UUID> {
    @Query("SELECT pm.id AS productModelId, " +
            "SUM(CASE WHEN p.isAvailable = true THEN 1 ELSE 0 END) AS availableProductCount, " +
            "COUNT(p.id) AS totalProductCount, " +
            "MIN(pr.amount) AS minPrice, " +
            "MAX(pr.amount) AS maxPrice " +
            "FROM ProductModel pm " +
            "LEFT JOIN Product p ON p.productModel.id = pm.id " +
            "LEFT JOIN Price pr ON pr.product.id = p.id " +  // Join with the Price entity
            "GROUP BY pm.id")
    List<Object[]> findProductModelAvailability();

}
