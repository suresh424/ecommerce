package com.talentica.ecommerce.product_order.repository;

import com.talentica.ecommerce.product_order.model.Order;
import com.talentica.ecommerce.product_order.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByCustomer(User user);
}
