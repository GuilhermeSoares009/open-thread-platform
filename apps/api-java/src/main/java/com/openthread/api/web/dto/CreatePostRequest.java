package com.openthread.api.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreatePostRequest(
        @NotNull UUID communityId,
        @NotBlank @Size(max = 200) String title,
        @NotBlank @Size(max = 8000) String body
) {
}
