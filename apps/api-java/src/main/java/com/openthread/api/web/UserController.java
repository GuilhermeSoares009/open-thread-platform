package com.openthread.api.web;

import com.openthread.api.service.UserService;
import com.openthread.api.web.dto.ActivityItemDto;
import com.openthread.api.web.dto.PaginatedResponse;
import com.openthread.api.web.dto.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable String userId) {
        return userService.get(userId);
    }

    @GetMapping("/{userId}/activity")
    public PaginatedResponse<ActivityItemDto> getActivity(
            @PathVariable String userId,
            @RequestParam(required = false) Integer page,
            @RequestParam(name = "per_page", required = false) Integer perPage
    ) {
        PaginationParams pagination = PaginationParams.from(page, perPage);
        return userService.getActivity(userId, pagination.page(), pagination.perPage());
    }
}
