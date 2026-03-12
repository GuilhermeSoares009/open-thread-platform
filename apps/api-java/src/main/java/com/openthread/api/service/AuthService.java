package com.openthread.api.service;

import com.openthread.api.domain.UserEntity;
import com.openthread.api.domain.UserRepository;
import com.openthread.api.web.error.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID requireUserId(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Missing bearer token");
        }

        String token = authHeader.replace("Bearer ", "").trim();
        UUID userId;
        try {
            userId = UUID.fromString(token);
        } catch (IllegalArgumentException exception) {
            throw new UnauthorizedException("Invalid bearer token format");
        }

        if (!userRepository.existsById(userId)) {
            throw new UnauthorizedException("Authenticated user not found");
        }

        return userId;
    }

    public UserEntity requireUser(HttpServletRequest request) {
        UUID userId = requireUserId(request);
        return userRepository.findById(userId)
                .orElseThrow(() -> new UnauthorizedException("Authenticated user not found"));
    }
}
