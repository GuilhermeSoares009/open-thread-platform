package com.openthread.api.web.dto;

import java.time.Instant;

public record CommunityDto(
        String id,
        String name,
        String slug,
        String description,
        Instant createdAt
) {
}
