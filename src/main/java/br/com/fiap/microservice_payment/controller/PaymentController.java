package br.com.fiap.microservice_payment.controller;

import br.com.fiap.microservice_payment.dto.PaymentDto;
import br.com.fiap.microservice_payment.dto.WebhookDto;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPRequest;
import com.mercadopago.resources.payment.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestController
@RequestMapping("payment")
public class PaymentController {

    @Value("${TOKEN}")
    private String TOKEN;

    @PostMapping()
    public String CreatePayment(@RequestBody PaymentDto paymentDto) {
        MercadoPagoConfig.setAccessToken(TOKEN);
        ZonedDateTime nowBrasilia = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));

        // Adiciona 3 minutos
        ZonedDateTime futureBrasilia = nowBrasilia.plusMinutes(1);

        // Converte para OffsetDateTime
        OffsetDateTime expirationDate = futureBrasilia.toOffsetDateTime();

        PaymentClient client = new PaymentClient();
        PaymentCreateRequest createRequest = PaymentCreateRequest.builder()
                .transactionAmount(new BigDecimal("0.01"))
                .installments(1)
                .paymentMethodId("pix")
                .dateOfExpiration(expirationDate)
//                .callbackUrl(paymentDto.url_callback)
//                .notificationUrl(paymentDto.url_callback)
                .payer(PaymentPayerRequest.builder().email("wagnersanches5@hotmail.com").build())
                .build();

//        Map<String, String> customHeaders = new HashMap<>();
//        customHeaders.put("x-idempotency-key", "1 23");

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

    @PostMapping("webhook")
    public String Webhook(@RequestBody WebhookDto webhookDto) throws MPException, MPApiException {
        System.out.println(webhookDto);
        PaymentClient client = new PaymentClient();

        System.out.println("------------> " + webhookDto.getData().getId());
        if(webhookDto.getAction().equals("payment.updated")) {
            Payment pay = client.get(webhookDto.getData().getId());
            System.out.println(pay.getStatus());
            System.out.println(pay.getStatusDetail());
        }

        return "ok";
    }
}
