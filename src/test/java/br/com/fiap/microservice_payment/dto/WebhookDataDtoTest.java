package br.com.fiap.microservice_payment.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebhookDataDtoTest {
    @Test
    public void testSettersAndGetters() {
        WebhookDataDto webhookDataDto = new WebhookDataDto();

        Long id = 123L;
        webhookDataDto.setId(id);

        assertEquals(id, webhookDataDto.getId());
    }
}
