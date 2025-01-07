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
    private String authorizationCode;
    private String typeOrder;
    private BigDecimal transactionAmount;

    public static PaymentEntity of(Payment payment) {
        return PaymentEntity.builder()
                .id(payment.getId())
                .operationType(payment.getOperationType())
                .status(payment.getStatus())
                .statusDetail(payment.getStatusDetail())
                .authorizationCode(payment.getAuthorizationCode())
                .typeOrder(payment.getOrder().getType())
                .transactionAmount(payment.getTransactionAmount())
                .build();
    }
}
