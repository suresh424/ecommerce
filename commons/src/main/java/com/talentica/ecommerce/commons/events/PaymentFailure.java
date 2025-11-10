package com.talentica.ecommerce.commons.events;

import java.time.Instant;

public record PaymentFailure(
        Long orderId,
        String provider,
        String reason,
        Instant failedAt
) {}