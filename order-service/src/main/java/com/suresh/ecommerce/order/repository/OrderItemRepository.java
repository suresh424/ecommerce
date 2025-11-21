package com.suresh.ecommerce.order.repository;

import com.suresh.ecommerce.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<Order, Long> {
}
