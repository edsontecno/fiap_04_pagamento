package br.com.fiap.microservice_payment.controller;

import br.com.fiap.microservice_payment.dto.PaymentDto;
import br.com.fiap.microservice_payment.dto.WebhookDto;
import br.com.fiap.microservice_payment.entity.PaymentEntity;
import br.com.fiap.microservice_payment.service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/{paymentId}")
    public PaymentEntity getPayment(@PathVariable String paymentId) {
        return paymentService.getPayment(paymentId);
    }

    @PostMapping("payment")
    public PaymentEntity createPayment(@RequestBody PaymentDto paymentDto) throws MPException, MPApiException, JsonProcessingException {
        return this.paymentService.createPayment(paymentDto);
    }

    @PostMapping("payment/webhook")
    public PaymentEntity webhook(@RequestBody WebhookDto webhookDto) throws MPException, MPApiException {
        return this.paymentService.webhookHandle(webhookDto);
    }
}
