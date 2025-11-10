package com.talentica.ecommerce.product_order.model;

import com.talentica.ecommerce.product_order.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "payment_transactions")
public class PaymentTransaction extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false, length = 50)
    private String provider = "SIMULATED";

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentStatus status = PaymentStatus.AUTHORIZED;

    @Column(length = 100)
    private String authCode;

    @Column(length = 256)
    private String failureReason;
}
