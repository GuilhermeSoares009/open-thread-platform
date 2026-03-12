package com.openthread.api.config;

import com.openthread.api.web.error.TooManyRequestsException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 20)
public class RateLimitFilter extends OncePerRequestFilter {

    private static final int LIMIT_PER_MINUTE = 60;
    private static final long WINDOW_SECONDS = 60;
    private static final Set<String> WRITE_METHODS = Set.of("POST", "PUT", "PATCH", "DELETE");
    private final ConcurrentHashMap<String, WindowState> buckets = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (!WRITE_METHODS.contains(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        String key = request.getRemoteAddr() + ':' + request.getMethod() + ':' + request.getRequestURI();
        long now = Instant.now().getEpochSecond();

        WindowState state = buckets.compute(key, (k, current) -> {
            if (current == null || now - current.windowStart > WINDOW_SECONDS) {
                return new WindowState(now, new AtomicInteger(1));
            }

            current.counter.incrementAndGet();
            return current;
        });

        if (state.counter.get() > LIMIT_PER_MINUTE) {
            throw new TooManyRequestsException("Too many write requests. Please retry later.");
        }

        filterChain.doFilter(request, response);
    }

    private record WindowState(long windowStart, AtomicInteger counter) {
    }
}
