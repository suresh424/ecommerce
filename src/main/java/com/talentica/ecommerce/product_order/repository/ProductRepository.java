package com.talentica.ecommerce.product_order.repository;

import com.talentica.ecommerce.product_order.model.Product;
import com.talentica.ecommerce.product_order.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findByCategory(Category category, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
