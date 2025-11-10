package com.talentica.ecommerce.commons.events;

import java.time.Instant;
import java.util.List;

public record InventoryUpdated(
        Long orderId,
        List<Item> items,
        Instant updatedAt
) {
    public record Item(Long productId, int deltaQuantity, int newQuantity) {}
}