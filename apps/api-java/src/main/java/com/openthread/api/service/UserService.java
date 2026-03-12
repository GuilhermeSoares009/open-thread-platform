package com.openthread.api.service;

import com.openthread.api.domain.UserEntity;
import com.openthread.api.domain.UserRepository;
import com.openthread.api.web.dto.ActivityItemDto;
import com.openthread.api.web.dto.PaginatedResponse;
import com.openthread.api.web.dto.UserDto;
import com.openthread.api.web.error.BadRequestException;
import com.openthread.api.web.error.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ActivityService activityService;
    private final DtoMapper dtoMapper;

    public UserService(UserRepository userRepository, ActivityService activityService, DtoMapper dtoMapper) {
        this.userRepository = userRepository;
        this.activityService = activityService;
        this.dtoMapper = dtoMapper;
    }

    public UserDto get(String userId) {
        UserEntity user = userRepository.findById(parseUuid(userId, "userId"))
                .orElseThrow(() -> new NotFoundException("User not found"));
        return dtoMapper.toUserDto(user);
    }

    public PaginatedResponse<ActivityItemDto> getActivity(String userId, int page, int perPage) {
        UUID parsedUserId = parseUuid(userId, "userId");
        if (!userRepository.existsById(parsedUserId)) {
            throw new NotFoundException("User not found");
        }
        return activityService.getByUser(parsedUserId, page, perPage);
    }

    private UUID parseUuid(String value, String field) {
        try {
            return UUID.fromString(value);
        } catch (Exception exception) {
            throw new BadRequestException("Invalid " + field + " UUID");
        }
    }
}
