package com.openthread.api.web;

import com.openthread.api.web.error.BadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PaginationParamsTest {

    @Test
    void shouldApplyDefaults() {
        PaginationParams params = PaginationParams.from(null, null);
        Assertions.assertEquals(1, params.page());
        Assertions.assertEquals(20, params.perPage());
    }

    @Test
    void shouldRejectInvalidPerPage() {
        Assertions.assertThrows(BadRequestException.class, () -> PaginationParams.from(1, 5));
        Assertions.assertThrows(BadRequestException.class, () -> PaginationParams.from(1, 100));
    }
}
