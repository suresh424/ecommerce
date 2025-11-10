package com.talentica.ecommerce.commons.events;

import java.time.Instant;

public record PaymentSuccess(
        Long orderId,
        String authCode,
        String provider,
        Instant authorizedAt,
        String invoiceUrl
) {}