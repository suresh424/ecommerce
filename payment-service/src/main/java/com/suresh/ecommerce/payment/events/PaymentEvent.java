package com.suresh.ecommerce.payment.events;

import lombok.Getter;

@Getter
public class PaymentEvent {

    private final Long orderId;
    private final String transactionId;
    private final String status;
    private final String failureReason;

    public PaymentEvent(Long orderId, String transactionId, String status, String failureReason) {
        this.orderId = orderId;
        this.transactionId = transactionId;
        this.status = status;
        this.failureReason = failureReason;
    }

}

