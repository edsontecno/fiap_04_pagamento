package br.com.fiap.microservice_payment.controller;

import br.com.fiap.microservice_payment.dto.PaymentDto;
import br.com.fiap.microservice_payment.dto.WebhookDto;
import br.com.fiap.microservice_payment.service.PaymentService;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("payment")
    public String createPayment(@RequestBody PaymentDto paymentDto) throws MPException, MPApiException {
        this.paymentService.createPayment(paymentDto);
        return "ok";
    }

    @PostMapping("payment/webhook")
    public String webhook(@RequestBody WebhookDto webhookDto) throws MPException, MPApiException {
        this.paymentService.webhookHandle(webhookDto);
        return "ok";
    }
}
