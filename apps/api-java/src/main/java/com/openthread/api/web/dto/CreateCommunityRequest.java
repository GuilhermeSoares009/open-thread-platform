package com.openthread.api.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCommunityRequest(
        @NotBlank @Size(max = 80) String name,
        @NotBlank @Size(max = 80) String slug,
        @Size(max = 280) String description
) {
}
