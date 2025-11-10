package com.talentica.ecommerce.product_order.repository;

import com.talentica.ecommerce.product_order.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findBySlug(String slug);
}
