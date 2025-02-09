package br.com.fiap.microservice_payment.controller;

import br.com.fiap.microservice_payment.dto.PaymentDto;
import br.com.fiap.microservice_payment.dto.WebhookDto;
import br.com.fiap.microservice_payment.entity.PaymentEntity;
import br.com.fiap.microservice_payment.service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PaymentControllertTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PaymentService service;

    @Autowired
    private ObjectMapper mapper;

    private final String PAYMENT_ID = "1";

    private PaymentEntity paymentEntity;

    @BeforeAll
    public void setup() {
        paymentEntity = PaymentEntity.builder()
                .id("1")
                .build();
    }

    @Order(1)
    @Test
    public void getPayment() throws Exception {
        given(this.service.getPayment(PAYMENT_ID)).willReturn(paymentEntity);
        this.mockMvc.perform(get("/payment/{paymentId}", this.PAYMENT_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("1")));
    }

    @Order(2)
    @Test
    public void createPayment() throws Exception {
        PaymentDto paymentDto = new PaymentDto();
        given(this.service.createPayment(any(PaymentDto.class))).willReturn(paymentEntity);
        this.mockMvc.perform(post("/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(paymentDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is("1")));
    }

    @Order(3)
    @Test
    public void webhook() throws Exception {
        WebhookDto webhookDto = new WebhookDto();
        given(this.service.webhookHandle(webhookDto)).willReturn(true);
        this.mockMvc.perform(post("/payment/webhook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(webhookDto)))
                .andDo(print())
                .andExpect(status().isOk());

    }
}
