package com.example.training.shop.keeper.publisher;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

@Component
public class SNSPublisher {
    private final String topicARN;
    private final SnsClient snsClient;

    @Autowired
    public SNSPublisher(SnsClient snsClient,
                        @Value("aws.sns.topic") String topicARN){
        this.snsClient = snsClient;
        this.topicARN = topicARN;
    }

    public void publishTopic(String code){
        PublishRequest request = PublishRequest.builder()
                .message(code)
                .topicArn(topicARN)
                .messageGroupId(code)
                .messageDeduplicationId(code)
                .build();
        PublishResponse publishResponse = snsClient.publish(request);
    }
}
