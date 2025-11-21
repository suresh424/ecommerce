package com.suresh.ecommerce.payment.grpc;

import com.suresh.ecommerce.payment.events.PaymentEvent;
import com.suresh.ecommerce.payment.events.PaymentEventPublisher;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Random;
import java.util.UUID;

@GrpcService
public class PaymentGrpcService extends PaymentServiceGrpc.PaymentServiceImplBase {

    private final PaymentEventPublisher eventPublisher;
    private final Random random = new Random();

    public PaymentGrpcService(PaymentEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void authorizePayment(
            PaymentAuthorizationRequest request,
            StreamObserver<PaymentAuthorizationResponse> responseObserver
    ) {
        boolean success = random.nextBoolean(); // simulate random success/failure
        String transactionId = UUID.randomUUID().toString();

        PaymentAuthorizationResponse.Builder builder =
                PaymentAuthorizationResponse.newBuilder()
                        .setTransactionId(transactionId);

        PaymentStatus status;
        String failureReason = "";

        if (success) {
            status = PaymentStatus.PAYMENT_STATUS_AUTHORIZED;
        } else {
            status = PaymentStatus.PAYMENT_STATUS_DECLINED;
            failureReason = "Mock payment declined by simulator";
        }

        builder.setStatus(status)
                .setFailureReason(failureReason);

        // Publish Kafka event
        String statusString = success ? "AUTHORIZED" : "DECLINED";
        PaymentEvent event = new PaymentEvent(
                request.getOrderId(),
                transactionId,
                statusString,
                failureReason
        );
        eventPublisher.publishPaymentEvent(event);

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getPaymentStatus(
            PaymentStatusRequest request,
            StreamObserver<PaymentStatusResponse> responseObserver
    ) {
        PaymentStatusResponse response = PaymentStatusResponse.newBuilder()
                .setStatus(PaymentStatus.PAYMENT_STATUS_AUTHORIZED)
                .setFailureReason("")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
