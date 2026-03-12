package com.openthread.api.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateVoteRequest(
        @NotBlank String votableType,
        @NotNull UUID votableId,
        @Min(-1) @Max(1) int value
) {
}
