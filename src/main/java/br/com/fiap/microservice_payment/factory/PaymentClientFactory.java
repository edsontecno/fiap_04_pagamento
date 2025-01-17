package br.com.fiap.microservice_payment.factory;

import com.mercadopago.client.payment.PaymentClient;

public interface PaymentClientFactory {
    public PaymentClient createPaymentClient();
}
