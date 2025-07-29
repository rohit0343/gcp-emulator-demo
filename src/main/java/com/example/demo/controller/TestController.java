package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.PubSubService;
import com.example.demo.service.SpannerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TestController {

    private final SpannerService spannerService;
    private final PubSubService pubSubService;

    public TestController(SpannerService spannerService, PubSubService pubSubService) {
        this.spannerService = spannerService;
        this.pubSubService = pubSubService;
    }

    @PostMapping("/spanner")
    public ResponseEntity<String> saveToSpanner(@RequestBody User entity) {
        spannerService.saveEntity(entity);
        return ResponseEntity.ok("Saved to Spanner");
    }

    @GetMapping("/spanner")
    public ResponseEntity<List<User>> getAllFromSpanner() {
        return ResponseEntity.ok(spannerService.fetchAll());
    }

    @PostMapping("/pubsub")
    public ResponseEntity<String> publishToPubSub(@RequestBody String message) {
        String messageId = pubSubService.publish(message);
        return ResponseEntity.ok("Published to Pub/Sub with messageId - "+messageId);
    }
}
