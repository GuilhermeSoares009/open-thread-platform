package com.openthread.api.web.error;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ApiException {
    public BadRequestException(String detail) {
        super(HttpStatus.BAD_REQUEST, "https://openthread.dev/problems/bad-request", "Bad Request", detail);
    }
}
