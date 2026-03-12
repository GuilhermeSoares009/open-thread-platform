package com.openthread.api.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateCommentRequest(
        UUID parentId,
        @NotBlank @Size(max = 2000) String body
) {
}
