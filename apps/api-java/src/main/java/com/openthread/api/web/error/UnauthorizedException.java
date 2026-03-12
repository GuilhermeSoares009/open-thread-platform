package com.openthread.api.web.error;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ApiException {
    public UnauthorizedException(String detail) {
        super(HttpStatus.UNAUTHORIZED, "https://openthread.dev/problems/unauthorized", "Unauthorized", detail);
    }
}
