package br.com.fiap.microservice_payment.service.impl;

import br.com.fiap.microservice_payment.dto.PaymentDto;
import br.com.fiap.microservice_payment.dto.WebhookDto;
import br.com.fiap.microservice_payment.entity.PaymentEntity;
import br.com.fiap.microservice_payment.exception.InvalidPaymentIdException;
import br.com.fiap.microservice_payment.exception.PaymentNotFoundedException;
import br.com.fiap.microservice_payment.repository.PaymentRepository;
import br.com.fiap.microservice_payment.service.PaymentService;
import br.com.fiap.microservice_payment.util.Constants;
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

import static br.com.fiap.microservice_payment.entity.PaymentEntity.of;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentClient client;
    private final PaymentRepository repository;

    public PaymentServiceImpl(@Value("${com.mercadopago.token}") String token,
                              PaymentRepository repository) {
        MercadoPagoConfig.setAccessToken(token);
        this.client = new PaymentClient();
        this.repository = repository;
    }

    public PaymentEntity getPayment(String paymentId) {

        if(paymentId == null || paymentId.isEmpty())
            throw new InvalidPaymentIdException();

        Long lPaymentId = Long.parseLong(paymentId);

        return repository.findById(lPaymentId).orElseThrow(PaymentNotFoundedException::new);
    }

    public PaymentEntity createPayment(PaymentDto paymentDto) throws MPException, MPApiException {

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

        return repository.save(of(client.create(createRequest, requestOptions)));
    }

    public PaymentEntity webhookHandle(WebhookDto webhookDto) throws MPException, MPApiException {
        System.out.println(webhookDto);
        PaymentClient client = new PaymentClient();
        System.out.println("ID ===== " + webhookDto.getId());
        if(webhookDto.getAction().equals("payment.updated")) {
            Payment pay = client.get(webhookDto.getData().getId());
            System.out.println(pay.getStatus());
            System.out.println(pay.getStatusDetail());
        }

        return null;
    }

}
