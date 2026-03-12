package com.openthread.api.web.dto;

import java.time.Instant;

public record PostDto(
        String id,
        String communityId,
        String userId,
        String title,
        String body,
        int score,
        int commentCount,
        Instant createdAt
) {
}
