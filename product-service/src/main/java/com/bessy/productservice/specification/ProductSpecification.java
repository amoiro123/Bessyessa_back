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

            if (filterDTO.getLoanUntil() != null && filterDTO.getLoanFrom() != null) {
                Join<Product, Reservation> reservationJoin = root.join("reservations");

                // Check if filterDTO.getLoanFrom() is within any reservation period
                Predicate loanFromWithinReservation = criteriaBuilder.and(
                        criteriaBuilder.greaterThanOrEqualTo(reservationJoin.get("loanedUntil"), filterDTO.getLoanFrom()),
                        criteriaBuilder.lessThanOrEqualTo(reservationJoin.get("loanedFrom"), filterDTO.getLoanFrom())
                );

                // Check if filterDTO.getLoanUntil() is within any reservation period
                Predicate loanUntilWithinReservation = criteriaBuilder.and(
                        criteriaBuilder.greaterThanOrEqualTo(reservationJoin.get("loanedUntil"), filterDTO.getLoanUntil()),
                        criteriaBuilder.lessThanOrEqualTo(reservationJoin.get("loanedFrom"), filterDTO.getLoanUntil())
                );

                // Exclude products if either loanFrom or loanUntil fall within an existing reservation
                Predicate dateOverlap = criteriaBuilder.and(loanFromWithinReservation, loanUntilWithinReservation);

                // Add NOT condition to exclude overlapping reservations
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.not(dateOverlap));
            }

            return predicate;
        };
    }
}
