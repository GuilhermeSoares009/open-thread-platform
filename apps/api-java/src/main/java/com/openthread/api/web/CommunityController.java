package com.openthread.api.web;

import com.openthread.api.service.AuthService;
import com.openthread.api.service.CommunityService;
import com.openthread.api.web.dto.CommunityDto;
import com.openthread.api.web.dto.CreateCommunityRequest;
import com.openthread.api.web.dto.PaginatedResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/communities")
public class CommunityController {

    private final CommunityService communityService;
    private final AuthService authService;

    public CommunityController(CommunityService communityService, AuthService authService) {
        this.communityService = communityService;
        this.authService = authService;
    }

    @GetMapping
    public PaginatedResponse<CommunityDto> list(
            @RequestParam(required = false) Integer page,
            @RequestParam(name = "per_page", required = false) Integer perPage
    ) {
        PaginationParams pagination = PaginationParams.from(page, perPage);
        return communityService.list(pagination.page(), pagination.perPage());
    }

    @GetMapping("/{communityId}")
    public CommunityDto get(@PathVariable String communityId) {
        return communityService.get(communityId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommunityDto create(@Valid @RequestBody CreateCommunityRequest request, HttpServletRequest httpRequest) {
        authService.requireUserId(httpRequest);
        return communityService.create(request);
    }
}
