package br.com.fiap.microservice_payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class MicroservicePaymentApplication {
	public static void main(String[] args) {
		SpringApplication.run(MicroservicePaymentApplication.class, args);
	}
}
