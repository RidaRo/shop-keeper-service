package com.example.training.shop.keeper.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;

import java.net.URI;


@Configuration
public class SNSConfig {

    private final String regionName;
    private final String awsEndpoint;
    private final String topicARN;

    public SNSConfig(
            @Value("${aws.region}") String regionName,
            @Value("${aws.sns.topic}") String topicARN,
            @Value("${aws.endpoint}") String awsEndpoint){
        this.awsEndpoint = awsEndpoint;
        this.regionName = regionName;
        this.topicARN = topicARN;
    }

    @Bean
    public SnsClient snsClient(){
        SubscribeRequest request = SubscribeRequest.builder()
                .protocol("sqs")
                .endpoint("http://localhost:7000/000000000000/item-updates")
                .returnSubscriptionArn(true)
                .topicArn(topicARN)
                .build();

        SnsClient snsClient = SnsClient.builder()
                .endpointOverride(URI.create(awsEndpoint))
                .region(Region.of(regionName))
                .build();
        snsClient.subscribe(request);

        return snsClient;
    }

}
