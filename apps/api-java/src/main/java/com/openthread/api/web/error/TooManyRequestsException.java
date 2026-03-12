package com.openthread.api.web.error;

import org.springframework.http.HttpStatus;

public class TooManyRequestsException extends ApiException {
    public TooManyRequestsException(String detail) {
        super(HttpStatus.TOO_MANY_REQUESTS, "https://openthread.dev/problems/rate-limit", "Too Many Requests", detail);
    }
}
