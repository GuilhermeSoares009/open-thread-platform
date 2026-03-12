package com.openthread.api.web.dto;

import java.time.Instant;

public record CommentDto(
        String id,
        String postId,
        String userId,
        String parentId,
        int depth,
        String body,
        int score,
        Instant createdAt
) {
}
