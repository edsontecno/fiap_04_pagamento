package br.com.fiap.microservice_payment.factory.impl;

import br.com.fiap.microservice_payment.factory.PaymentClientFactory;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaymentClientFactoryImpl implements PaymentClientFactory {

    @Value("${com.mercadopago.token}")
    private String token;

    private PaymentClient client;

    public PaymentClient createPaymentClient() {
        MercadoPagoConfig.setAccessToken(token);

        if(this.client == null) {
            MercadoPagoConfig.setAccessToken(token);
            this.client = new PaymentClient();
        }

        return this.client;
    }
}
