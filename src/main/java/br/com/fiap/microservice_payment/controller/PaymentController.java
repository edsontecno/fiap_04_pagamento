package br.com.fiap.microservice_payment.controller;

import br.com.fiap.microservice_payment.dto.PaymentDto;
import br.com.fiap.microservice_payment.dto.WebhookDto;
import br.com.fiap.microservice_payment.entity.PaymentEntity;
import br.com.fiap.microservice_payment.service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("{paymentId}")
    public ResponseEntity<PaymentEntity> getPayment(@PathVariable String paymentId) {
        return ResponseEntity.ok(paymentService.getPayment(paymentId));
    }

    @PostMapping
    public ResponseEntity<PaymentEntity> createPayment(@RequestBody PaymentDto paymentDto) throws MPException, MPApiException, JsonProcessingException, URISyntaxException {
        PaymentEntity paymentEntity = this.paymentService.createPayment(paymentDto);
        return ResponseEntity.created(new URI("/payment/" + paymentEntity.getId())).body(paymentEntity);
    }

    @PostMapping("webhook")
    public ResponseEntity<String> webhook(@RequestBody WebhookDto webhookDto) throws MPException, MPApiException {
        this.paymentService.webhookHandle(webhookDto);
        return ResponseEntity.ok("Received!");
    }
}
