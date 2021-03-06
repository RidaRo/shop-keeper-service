package com.example.training.shop.keeper.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

import java.net.URI;


@Configuration
public class SNSConfig {

    private final String region;
    private final String awsEndpoint;

    public SNSConfig(
            @Value("${aws.region}") String region,
            @Value("${aws.endpoint}") String awsEndpoint){
        this.awsEndpoint = awsEndpoint;
        this.region = region;
    }

    @Bean
    public SnsClient snsClient(){
        return SnsClient.builder()
                .endpointOverride(URI.create(awsEndpoint))
                .region(Region.of(region))
                .build();
    }

}
