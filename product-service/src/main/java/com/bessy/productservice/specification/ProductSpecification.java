package com.bessy.productservice.specification;

import com.bessy.productservice.dto.ProductFilterDTO;
import com.bessy.productservice.model.Price;
import com.bessy.productservice.model.Product;
import com.bessy.productservice.model.ProductModel;
import com.bessy.productservice.model.Reservation;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class ProductSpecification {

    public static Specification<Product> filterProducts(ProductFilterDTO filterDTO) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction(); // Create a base conjunction (AND)

            if (filterDTO.getProductModelId() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("productModel").get("id"), filterDTO.getProductModelId()));
            }

            if (filterDTO.getProductModelName() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(root.get("productModel").get("name"), filterDTO.getProductModelName()));
            }

            if (filterDTO.getBrandId() != null) {
                Join<Product, ProductModel> productModelJoin = root.join("productModel");
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(productModelJoin.get("brand").get("id"), filterDTO.getBrandId()));
            }

            if (filterDTO.getMinPrice() != null) {
                Join<Product, Price> priceJoin = root.join("currentPrice");
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.greaterThanOrEqualTo(priceJoin.get("amount"), filterDTO.getMinPrice()));
            }

            if (filterDTO.getMaxPrice() != null) {
                Join<Product, Price> priceJoin = root.join("currentPrice");
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.lessThanOrEqualTo(priceJoin.get("amount"), filterDTO.getMaxPrice()));
            }

            if (filterDTO.getLoanFrom() != null || filterDTO.getLoanUntil() != null) {
                Join<Product, Reservation> reservationJoin = root.join("reservations", JoinType.LEFT);
                predicate = criteriaBuilder.or(
                        criteriaBuilder.lessThan(reservationJoin.get("loanedUntil"), filterDTO.getLoanUntil()),
                        criteriaBuilder.greaterThan(reservationJoin.get("loanedFrom"), filterDTO.getLoanFrom())
                );
            }
            return predicate;
        };
    }
}
