package com.example.demo.service;

import com.example.demo.entity.User;
import com.google.cloud.spring.data.spanner.core.SpannerTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpannerService {

    private final SpannerTemplate spannerTemplate;

    public SpannerService(SpannerTemplate spannerTemplate) {
        this.spannerTemplate = spannerTemplate;
    }

    public void saveEntity(User entity) {
        spannerTemplate.insert(entity);
    }

    public List<User> fetchAll() {
        return spannerTemplate.readAll(User.class);
    }
}

