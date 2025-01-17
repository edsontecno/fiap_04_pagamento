package br.com.fiap.microservice_payment.dto;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

public class WebhookDtoTest {
    @Test
    public void testAllArgsConstructor() {
        String action = "CREATE";
        String apiVersion = "1.0";
        WebhookDataDto data = new WebhookDataDto();
        String dateCreated = "2025-01-15";
        String id = "abc123";
        Boolean liveMode = true;
        String type = "event";
        BigInteger userId = new BigInteger("123456");

        WebhookDto webhookDto = new WebhookDto(action, apiVersion, data, dateCreated, id, liveMode, type, userId);

        assertEquals(action, webhookDto.getAction());
        assertEquals(apiVersion, webhookDto.getApi_version());
        assertEquals(data, webhookDto.getData());
        assertEquals(dateCreated, webhookDto.getDate_created());
        assertEquals(id, webhookDto.getId());
        assertEquals(liveMode, webhookDto.getLive_mode());
        assertEquals(type, webhookDto.getType());
        assertEquals(userId, webhookDto.getUser_id());
    }

    @Test
    public void testNoArgsConstructor() {
        WebhookDto webhookDto = new WebhookDto();

        assertNull(webhookDto.getAction());
        assertNull(webhookDto.getApi_version());
        assertNull(webhookDto.getData());
        assertNull(webhookDto.getDate_created());
        assertNull(webhookDto.getId());
        assertNull(webhookDto.getLive_mode());
        assertNull(webhookDto.getType());
        assertNull(webhookDto.getUser_id());
    }

    @Test
    public void testSettersAndGetters() {
        WebhookDto webhookDto = new WebhookDto();

        String action = "UPDATE";
        String apiVersion = "2.0";
        WebhookDataDto data = new WebhookDataDto();
        String dateCreated = "2025-01-16";
        String id = "xyz789";
        Boolean liveMode = false;
        String type = "update";
        BigInteger userId = new BigInteger("654321");

        webhookDto.setAction(action);
        webhookDto.setApi_version(apiVersion);
        webhookDto.setData(data);
        webhookDto.setDate_created(dateCreated);
        webhookDto.setId(id);
        webhookDto.setLive_mode(liveMode);
        webhookDto.setType(type);
        webhookDto.setUser_id(userId);

        assertEquals(action, webhookDto.getAction());
        assertEquals(apiVersion, webhookDto.getApi_version());
        assertEquals(data, webhookDto.getData());
        assertEquals(dateCreated, webhookDto.getDate_created());
        assertEquals(id, webhookDto.getId());
        assertEquals(liveMode, webhookDto.getLive_mode());
        assertEquals(type, webhookDto.getType());
        assertEquals(userId, webhookDto.getUser_id());
    }

    @Test
    public void testToString() {
        WebhookDto webhookDto = new WebhookDto();
        webhookDto.setAction("DELETE");
        webhookDto.setApi_version("3.0");

        String toStringResult = webhookDto.toString();

        assertTrue(toStringResult.contains("DELETE"));
        assertTrue(toStringResult.contains("3.0"));
    }
}
