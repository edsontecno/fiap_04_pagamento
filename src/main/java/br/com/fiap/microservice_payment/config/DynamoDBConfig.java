package br.com.fiap.microservice_payment.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

import org.apache.commons.lang.StringUtils;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDynamoDBRepositories(basePackages = "br.com.fiap.microservice_payment.repository")
public class DynamoDBConfig {

    // @Value("${amazon.dynamodb.endpoint}")
    // private String amazonDynamoDBEndpoint;

    // @Value("${amazon.aws.accesskey}")
    // private String amazonAWSAccessKey;

    // @Value("${amazon.aws.secretkey}")
    // private String amazonAWSSecretKey;

    private String awsRegion = "us-east-1";

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        AmazonDynamoDBClientBuilder builder = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion(awsRegion)
            .withCredentials(DefaultAWSCredentialsProviderChain.getInstance());

        // if (dynamoDBEndpoint != null && !dynamoDBEndpoint.isEmpty()) {
        //     builder.withEndpointConfiguration(
        //             new AwsClientBuilder.EndpointConfiguration(dynamoDBEndpoint, awsRegion));
        // } else {
        //     builder.withRegion(awsRegion)
        //     .withCredentials(DefaultAWSCredentialsProviderChain.getInstance());
        // }

        return builder.build();
    }
}
