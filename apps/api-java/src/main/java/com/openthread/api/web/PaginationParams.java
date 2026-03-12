package com.openthread.api.web;

import com.openthread.api.web.error.BadRequestException;

public record PaginationParams(int page, int perPage) {

    public static PaginationParams from(Integer page, Integer perPage) {
        int normalizedPage = page == null ? 1 : page;
        int normalizedPerPage = perPage == null ? 20 : perPage;

        if (normalizedPage < 1) {
            throw new BadRequestException("page must be >= 1");
        }

        if (normalizedPerPage < 10 || normalizedPerPage > 50) {
            throw new BadRequestException("per_page must be between 10 and 50");
        }

        return new PaginationParams(normalizedPage, normalizedPerPage);
    }
}
