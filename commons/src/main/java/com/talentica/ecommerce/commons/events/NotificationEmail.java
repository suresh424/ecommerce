package com.talentica.ecommerce.commons.events;

import java.time.Instant;

public record NotificationEmail(
        String toEmail,
        String subject,
        String body,
        Instant queuedAt,
        String correlationId
) {}