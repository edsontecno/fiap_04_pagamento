package br.com.fiap.microservice_payment.entity;

import com.mercadopago.resources.payment.Payment;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PaymentEntityTest {

    @Test
    public void testBuilder() {
        PaymentEntity paymentEntity = PaymentEntity.builder()
                .id(1L)
                .operationType("payment")
                .status("approved")
                .statusDetail("accredited")
                .transactionAmount(new BigDecimal("100.00"))
                .qrCode("qrCode123")
                .qrCodeBase64("qrCodeBase64==")
                .ticketUrl("http://ticket.url")
                .issuerId("issuer123")
                .paymentMethodId("visa")
                .build();

        assertEquals("payment", paymentEntity.getOperationType());
        assertEquals("approved", paymentEntity.getStatus());
        assertEquals("accredited", paymentEntity.getStatusDetail());
        assertEquals(new BigDecimal("100.00"), paymentEntity.getTransactionAmount());
        assertEquals("qrCode123", paymentEntity.getQrCode());
        assertEquals("qrCodeBase64==", paymentEntity.getQrCodeBase64());
        assertEquals("http://ticket.url", paymentEntity.getTicketUrl());
        assertEquals("issuer123", paymentEntity.getIssuerId());
        assertEquals("visa", paymentEntity.getPaymentMethodId());
    }

}
