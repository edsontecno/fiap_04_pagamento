package br.com.fiap.microservice_payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class WebhookDto {
    private String action;
    private String api_version;
    private WebhookDataDto data;
    private String date_created;
    private String id;
    private Boolean live_mode;
    private String type;
    private BigInteger user_id;
}
