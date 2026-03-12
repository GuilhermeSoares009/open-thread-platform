package com.openthread.api.web.error;

import java.util.Map;

public record ProblemResponse(
        String type,
        String title,
        int status,
        String detail,
        String instance,
        Map<String, Object> errors
) {
}
