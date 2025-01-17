package br.com.fiap.microservice_payment.entity;

import br.com.fiap.microservice_payment.mock.PaymentMock;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mercadopago.resources.payment.Payment;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PaymentEntityTest {

    @Test
    public void testBuilder() throws JsonProcessingException {
        Payment payment = PaymentMock.getPayment();
        PaymentEntity result = PaymentEntity.of(payment);

        assertNotNull(result);
        assertEquals("regular_payment", result.getOperationType());
        assertEquals("pending", result.getStatus());
        assertEquals("pending_waiting_transfer", result.getStatusDetail());
        assertEquals(new BigDecimal("0.01"), result.getTransactionAmount());
        assertEquals("12501", result.getIssuerId());
        assertEquals("pix", result.getPaymentMethodId());
    }

}
