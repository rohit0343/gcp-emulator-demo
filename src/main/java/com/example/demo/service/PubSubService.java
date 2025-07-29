package com.example.demo.service;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.common.util.concurrent.ListenableFuture;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class PubSubService {

    private final PubSubTemplate pubSubTemplate;

    @Value("${gcp.pubsub.topic}")
    private String topic;

    public PubSubService(PubSubTemplate pubSubTemplate) {
        this.pubSubTemplate = pubSubTemplate;
    }

    public String publish(String message) {
        CompletableFuture<String> future = pubSubTemplate.publish(topic, message);
        try {
            return future.get();
        } catch (Exception e) {
            throw new RuntimeException("Failed to publish message to Pub/Sub", e);
        }
    }
}
