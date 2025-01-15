package br.com.fiap.microservice_payment.service;

import br.com.fiap.microservice_payment.dto.PaymentDto;
import br.com.fiap.microservice_payment.dto.WebhookDto;
import br.com.fiap.microservice_payment.entity.PaymentEntity;
import br.com.fiap.microservice_payment.exception.InvalidPaymentIdException;
import br.com.fiap.microservice_payment.exception.PaymentNotFoundedException;
import br.com.fiap.microservice_payment.repository.PaymentRepository;
import br.com.fiap.microservice_payment.service.impl.PaymentServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.util.Assert.isTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepository repository;

    @Mock
    private PaymentClient paymentClient;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Order(1)
    @Test
    public void getPayment_null_and_empty() {
        assertThrows(InvalidPaymentIdException.class, () -> {
            this.paymentService.getPayment("");
        });

        assertThrows(InvalidPaymentIdException.class, () -> {
            this.paymentService.getPayment(null);
        });
    }

    @Order(2)
    @Test
    public void getPayment() {
        Long lPaymentId = 1L;
        Optional<PaymentEntity> payment = Optional.of(
                PaymentEntity.builder().id(lPaymentId).build());
        given(this.repository.findById(lPaymentId)).willReturn(payment);

        assertThat(this.paymentService.getPayment("1").getId()).isEqualTo(lPaymentId);
    }

    @Order(3)
    @Test
    public void getPayment_throwNotFounded() {
        Long lPaymentId = 1L;
        given(this.repository.findById(lPaymentId)).willThrow(PaymentNotFoundedException.class);

        assertThrows(PaymentNotFoundedException.class, () -> {
            this.paymentService.getPayment("1");
        });
    }

    @Order(4)
    @Test
    public void webhookHandle_diffAction() throws MPException, MPApiException, JsonProcessingException {
        WebhookDto webhookDto = new WebhookDto();
        webhookDto.setAction("");
        assertThat(this.paymentService.webhookHandle(webhookDto)).isEqualTo(false);
    }
}
