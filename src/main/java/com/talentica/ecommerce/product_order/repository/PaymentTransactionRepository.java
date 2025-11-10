package com.talentica.ecommerce.product_order.repository;

import com.talentica.ecommerce.product_order.model.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Integer> {
}
