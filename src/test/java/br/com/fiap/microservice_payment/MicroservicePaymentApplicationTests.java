//package br.com.fiap.microservice_payment;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//
//@SpringBootTest
//public class MicroservicePaymentApplicationTests {
//
//    @DynamicPropertySource
//    static void configureProperties(DynamicPropertyRegistry registry) {
//        registry.add("database_url", () -> "jdbc:h2:mem:testdb");
//        registry.add("mercadopago_token", () -> "valorTeste");
//        registry.add("aws_accesskey", () -> "valorTeste");
//        registry.add("aws_secretkey", () -> "valorTeste");
//        registry.add("AWS_SESSION_TOKEN", () -> "valorTeste");
//    }
//
//    @Test
//    void contextLoads() {
//    }
//}
