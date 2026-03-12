package com.openthread.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openthread.api.domain.ActivityEntity;
import com.openthread.api.domain.ActivityRepository;
import com.openthread.api.domain.ActivityType;
import com.openthread.api.web.dto.ActivityItemDto;
import com.openthread.api.web.dto.PaginatedResponse;
import com.openthread.api.web.dto.PaginationMeta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ObjectMapper objectMapper;

    public ActivityService(ActivityRepository activityRepository, ObjectMapper objectMapper) {
        this.activityRepository = activityRepository;
        this.objectMapper = objectMapper;
    }

    public void log(UUID userId, ActivityType type, Map<String, Object> payload) {
        ActivityEntity activity = new ActivityEntity();
        activity.setUserId(userId);
        activity.setType(type);
        try {
            activity.setPayload(objectMapper.writeValueAsString(payload));
        } catch (JsonProcessingException exception) {
            activity.setPayload("{}");
        }

        activityRepository.save(activity);
    }

    public PaginatedResponse<ActivityItemDto> getByUser(UUID userId, int page, int perPage) {
        Page<ActivityEntity> activities = activityRepository.findByUserIdOrderByCreatedAtDesc(userId, PageRequest.of(page - 1, perPage));
        return new PaginatedResponse<>(
                activities.stream().map(this::toDto).toList(),
                new PaginationMeta(page, perPage, activities.getTotalElements())
        );
    }

    private ActivityItemDto toDto(ActivityEntity activity) {
        Map<String, Object> parsedPayload;
        try {
            parsedPayload = objectMapper.readValue(activity.getPayload(), new TypeReference<>() {
            });
        } catch (Exception exception) {
            parsedPayload = Map.of("raw", activity.getPayload());
        }
        return new ActivityItemDto(activity.getType().name().toLowerCase(), parsedPayload, activity.getCreatedAt());
    }
}
