package br.com.fiap.microservice_payment.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PaymentDtoTest {
    @Test
    public void testAllArgsConstructor() {
        String idempotencyKey = "12345";
        BigDecimal amount = new BigDecimal("100.00");
        String clientEmail = "client@example.com";

        PaymentDto paymentDto = new PaymentDto(idempotencyKey, amount, clientEmail);

        assertEquals(idempotencyKey, paymentDto.getIdempotencyKey());
        assertEquals(amount, paymentDto.getAmount());
        assertEquals(clientEmail, paymentDto.getClientEmail());
    }

    @Test
    public void testNoArgsConstructor() {
        PaymentDto paymentDto = new PaymentDto();

        assertNull(paymentDto.getIdempotencyKey());
        assertNull(paymentDto.getAmount());
        assertNull(paymentDto.getClientEmail());
    }

    @Test
    public void testSettersAndGetters() {
        PaymentDto paymentDto = new PaymentDto();

        String idempotencyKey = "67890";
        BigDecimal amount = new BigDecimal("200.00");
        String clientEmail = "newclient@example.com";

        paymentDto.setIdempotencyKey(idempotencyKey);
        paymentDto.setAmount(amount);
        paymentDto.setClientEmail(clientEmail);

        assertEquals(idempotencyKey, paymentDto.getIdempotencyKey());
        assertEquals(amount, paymentDto.getAmount());
        assertEquals(clientEmail, paymentDto.getClientEmail());
    }
}
