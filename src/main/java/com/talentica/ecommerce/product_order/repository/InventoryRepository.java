package com.talentica.ecommerce.product_order.repository;

import com.talentica.ecommerce.product_order.model.Inventory;
import com.talentica.ecommerce.product_order.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    Optional<Inventory> findByProduct(Product product);
}
