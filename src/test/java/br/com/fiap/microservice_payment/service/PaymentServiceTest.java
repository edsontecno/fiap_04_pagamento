package br.com.fiap.microservice_payment.service;

import br.com.fiap.microservice_payment.dto.PaymentDto;
import br.com.fiap.microservice_payment.dto.WebhookDataDto;
import br.com.fiap.microservice_payment.dto.WebhookDto;
import br.com.fiap.microservice_payment.entity.PaymentEntity;
import br.com.fiap.microservice_payment.exception.InvalidPaymentIdException;
import br.com.fiap.microservice_payment.exception.PaymentNotFoundedException;
import br.com.fiap.microservice_payment.factory.PaymentClientFactory;
import br.com.fiap.microservice_payment.mock.PaymentMock;
import br.com.fiap.microservice_payment.repository.PaymentRepository;
import br.com.fiap.microservice_payment.service.impl.PaymentServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import org.junit.jupiter.api.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.util.Assert.isTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {
    @Mock
    private PaymentRepository repository;

    @Mock
    private PaymentClientFactory clientFactory;

    @Mock
    private PaymentClient paymentClient;

    private PaymentServiceImpl paymentService;

    @BeforeEach
    void setUp() {
        when(clientFactory.createPaymentClient()).thenReturn(paymentClient);
        paymentService = new PaymentServiceImpl(repository, clientFactory);
    }

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
        String lPaymentId = "1";
        Optional<PaymentEntity> payment = Optional.of(
                PaymentEntity.builder().id(lPaymentId).build());
        given(this.repository.findById(lPaymentId)).willReturn(payment);

        assertThat(this.paymentService.getPayment("1").getId()).isEqualTo(lPaymentId);
    }

    @Order(3)
    @Test
    public void getPayment_throwNotFounded() {
        String lPaymentId = "1";
        Optional<PaymentEntity> optional = Optional.empty();
        given(this.repository.findById(lPaymentId)).willReturn(optional);

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

    @Order(5)
    @Test
    public void webhookHandle_sucessful() throws MPException, MPApiException, JsonProcessingException {
        WebhookDto webhookDto = new WebhookDto();
        webhookDto.setAction("payment.updated");
        WebhookDataDto data = new WebhookDataDto();
        data.setId(1L);
        webhookDto.setData(data);

        Payment payment = PaymentMock.getPayment();
        PaymentEntity paymentEntity = PaymentEntity.of(payment);
        when(paymentClient.get(any())).thenReturn(payment);
        when(repository.save(any(PaymentEntity.class))).thenReturn(paymentEntity);
        assertThat(this.paymentService.webhookHandle(webhookDto)).isEqualTo(true);

    }

    @Order(6)
    @Test
    public void createPayment_sucesss() throws Exception {
        Payment payment = PaymentMock.getPayment();
        PaymentEntity paymentEntity = PaymentEntity.of(payment);

        PaymentDto paymentDto = new PaymentDto("123", new BigDecimal("1") ,"w@w.com");
        when(paymentClient.create(any(), any())).thenReturn(payment);
        when(repository.save(any(PaymentEntity.class))).thenReturn(paymentEntity);
        PaymentEntity response = this.paymentService.createPayment(paymentDto);

        assertThat(response.getId()).isEqualTo(paymentEntity.getId());
    }
}