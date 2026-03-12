package com.openthread.api.web.error;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiException {
    public NotFoundException(String detail) {
        super(HttpStatus.NOT_FOUND, "https://openthread.dev/problems/not-found", "Not Found", detail);
    }
}
