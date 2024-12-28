package br.com.fiap.microservice_payment.controller;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("payment")
public class PaymentController {

    @Value("${TOKEN}")
    private String TOKEN;

    @GetMapping()
    public String CreatePayment() {
        MercadoPagoConfig.setAccessToken(TOKEN);

        PaymentClient client = new PaymentClient();
        PaymentCreateRequest createRequest = PaymentCreateRequest.builder()
                .transactionAmount(new BigDecimal("0.1"))
                .installments(1)
                .paymentMethodId("pix")
                .payer(PaymentPayerRequest.builder().email("wagnersanches5@hotmail.com").build())
                .build();

//        Map<String, String> customHeaders = new HashMap<>();
//        customHeaders.put("x-idempotency-key", "123");

        try {
            Payment payment = client.create(createRequest);
            System.out.println(payment.getPointOfInteraction().getTransactionData().getTicketUrl());
        } catch (MPApiException ex) {
            System.out.printf(
                    "MercadoPago Error. Status: %s, Content: %s%n",
                    ex.getApiResponse().getStatusCode(), ex.getApiResponse().getContent());
        } catch (MPException ex) {
            ex.printStackTrace();
        }

        return "OK";
    }

}
