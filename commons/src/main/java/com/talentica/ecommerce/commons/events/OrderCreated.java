package com.talentica.ecommerce.commons.events;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderCreated(
        Long orderId,
        String requestId,
        String currency,
        BigDecimal subtotal,
        BigDecimal tax,
        BigDecimal shippingFee,
        BigDecimal total,
        String customerId,
        List<OrderItem> items,
        Instant createdAt
) {
    public record OrderItem(Long productId, int quantity, BigDecimal unitPrice) {}
}