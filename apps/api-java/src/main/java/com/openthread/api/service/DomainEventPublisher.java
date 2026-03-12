package com.openthread.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DomainEventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(DomainEventPublisher.class);
    private static final String STREAM_KEY = "openthread.events";

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public DomainEventPublisher(StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public void publish(String type, Map<String, Object> payload) {
        try {
            String jsonPayload = objectMapper.writeValueAsString(payload);
            redisTemplate.opsForStream().add(STREAM_KEY, Map.of(
                    "type", type,
                    "payload", jsonPayload
            ));
        } catch (JsonProcessingException exception) {
            logger.warn("Could not serialize event payload", exception);
        } catch (Exception exception) {
            logger.warn("Could not publish event to Redis stream", exception);
        }
    }
}
