package br.com.fiap.microservice_payment.service.impl;

import br.com.fiap.microservice_payment.dto.PaymentDto;
import br.com.fiap.microservice_payment.dto.WebhookDto;
import br.com.fiap.microservice_payment.entity.PaymentEntity;
import br.com.fiap.microservice_payment.exception.InvalidPaymentIdException;
import br.com.fiap.microservice_payment.exception.PaymentNotFoundedException;
import br.com.fiap.microservice_payment.factory.PaymentClientFactory;
import br.com.fiap.microservice_payment.repository.PaymentRepository;
import br.com.fiap.microservice_payment.service.PaymentService;
import br.com.fiap.microservice_payment.util.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static br.com.fiap.microservice_payment.entity.PaymentEntity.of;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentClient client;
    private final PaymentRepository repository;

    public PaymentServiceImpl(PaymentRepository repository,
                              PaymentClientFactory clientFactory) {
        this.repository = repository;
        this.client = clientFactory.createPaymentClient();
    }

    public PaymentEntity getPayment(String paymentId) {

        if(paymentId == null || paymentId.isEmpty())
            throw new InvalidPaymentIdException();

        Long lPaymentId = Long.parseLong(paymentId);
        Optional<PaymentEntity> paymentEntity = repository.findById(lPaymentId);

        if(paymentEntity.isEmpty())
            throw new PaymentNotFoundedException();

        return paymentEntity.get();
    }

    public PaymentEntity createPayment(PaymentDto paymentDto) throws MPException, MPApiException, JsonProcessingException {

        Map<String, String> customHeaders = new HashMap<>();
        customHeaders.put("x-idempotency-key", paymentDto.getIdempotencyKey());

        MPRequestOptions requestOptions = MPRequestOptions.builder()
                .customHeaders(customHeaders)
                .build();

        PaymentCreateRequest createRequest = PaymentCreateRequest.builder()
                .transactionAmount(paymentDto.getAmount())
                .installments(Constants.PAYMENT_INSTALLMENTS)
                .paymentMethodId(Constants.PAYMENT_METHOD_PIX)
                .description(Constants.PAYMENT_DESCRIPTION)
                .payer(PaymentPayerRequest.builder()
                        .email(paymentDto.getClientEmail())
                        .build())
                .build();

        Payment payment = client.create(createRequest, requestOptions);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        return repository.save(of(payment));
    }

    public boolean webhookHandle(WebhookDto webhookDto) throws MPException, MPApiException {
        if(webhookDto.getAction().equals("payment.updated")) {
            Payment payment = client.get(webhookDto.getData().getId());
            repository.save(of(payment));
            return true;
        }

        return false;
    }

}
