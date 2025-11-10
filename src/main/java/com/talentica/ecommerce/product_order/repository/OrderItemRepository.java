package com.talentica.ecommerce.product_order.repository;

import com.talentica.ecommerce.product_order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
