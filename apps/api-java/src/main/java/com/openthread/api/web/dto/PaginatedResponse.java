package com.openthread.api.web.dto;

import java.util.List;

public record PaginatedResponse<T>(List<T> data, PaginationMeta pagination) {
}
