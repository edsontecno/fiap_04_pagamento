package br.com.fiap.microservice_payment.service;

import br.com.fiap.microservice_payment.dto.PaymentDto;
import br.com.fiap.microservice_payment.dto.WebhookDto;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;

public interface PaymentService {
    void createPayment(PaymentDto paymentDto) throws MPException, MPApiException;
    void webhookHandle(WebhookDto webhookDto) throws MPException, MPApiException;
}
