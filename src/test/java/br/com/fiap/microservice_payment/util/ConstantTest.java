package br.com.fiap.microservice_payment.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConstantTest {

    @Test
    public void constructor() {
        Constants constants = new Constants();
        assertNotNull(constants);
    }

}
