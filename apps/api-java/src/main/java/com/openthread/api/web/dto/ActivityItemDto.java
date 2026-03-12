package com.openthread.api.web.dto;

import java.time.Instant;
import java.util.Map;

public record ActivityItemDto(
        String type,
        Map<String, Object> payload,
        Instant createdAt
) {
}
