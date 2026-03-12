package com.openthread.api.web.dto;

public record VoteDto(
        String id,
        String userId,
        String votableType,
        String votableId,
        int value
) {
}
