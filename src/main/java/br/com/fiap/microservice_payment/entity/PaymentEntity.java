package br.com.fiap.microservice_payment.entity;

import com.mercadopago.resources.payment.Payment;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document
@Getter
@Setter
@Builder
public class PaymentEntity {

    @Id
    private Long id;
    private String operationType;
    private String status;
    private String statusDetail;
    private BigDecimal transactionAmount;

    private String qrCode;
    private String qrCodeBase64;
    private String ticketUrl;
    private String issuerId;
    private String paymentMethodId;

    public static PaymentEntity of(Payment payment) {
        return PaymentEntity.builder()
                .id(payment.getId())
                .operationType(payment.getOperationType())
                .status(payment.getStatus())
                .statusDetail(payment.getStatusDetail())
                .qrCode(payment.getPointOfInteraction().getTransactionData().getQrCode())
                .qrCodeBase64(payment.getPointOfInteraction().getTransactionData().getQrCodeBase64())
                .ticketUrl(payment.getPointOfInteraction().getTransactionData().getTicketUrl())
                .transactionAmount(payment.getTransactionAmount())
                .issuerId(payment.getIssuerId())
                .paymentMethodId(payment.getPaymentMethodId())
                .build();
    }
}
