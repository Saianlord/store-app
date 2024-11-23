package com.store.store_app.specifications;

import com.store.store_app.models.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

    public static Specification<Product> matchesName(String providedName) {
        return (root, query, criteriaBuilder) -> {
            System.out.println("Ejecutando filtro 'matchesName' con: " + providedName);  // Log para el filtro de nombre
            if (providedName == null || providedName.isEmpty()) {
                return criteriaBuilder.conjunction(); // No aplica filtro
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    "%" + providedName.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Product> hasCategory(String providedCategory) {
        return (root, query, criteriaBuilder) -> {
            System.out.println("Ejecutando filtro 'hasCategory' con: " + providedCategory);  // Log para el filtro de categoría
            if (providedCategory == null || providedCategory.isEmpty()) {
                return criteriaBuilder.conjunction(); // No aplica filtro
            }
            return criteriaBuilder.equal(
                    criteriaBuilder.lower(root.get("category").get("name")),
                    providedCategory.toLowerCase()
            );
        };
    }

    public static Specification<Product> lessThanOrEqualTo(Float providedPrice) {
        return (root, query, criteriaBuilder) -> {
            if (providedPrice == null) {
                return criteriaBuilder.conjunction();
            }
            // Añadir orden ascendente por precio
            query.orderBy(criteriaBuilder.asc(root.get("price")));
            return criteriaBuilder.lessThanOrEqualTo(root.get("price"), providedPrice);
        };
    }
}