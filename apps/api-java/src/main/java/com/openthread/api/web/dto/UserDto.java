package com.openthread.api.web.dto;

public record UserDto(
        String id,
        String name,
        String username,
        String avatarUrl,
        String bio
) {
}
