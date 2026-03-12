package com.openthread.api.web;

import com.openthread.api.service.PostService;
import com.openthread.api.web.dto.PaginatedResponse;
import com.openthread.api.web.dto.PostDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feed")
public class FeedController {

    private final PostService postService;

    public FeedController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public PaginatedResponse<PostDto> feed(
            @RequestParam(required = false) Integer page,
            @RequestParam(name = "per_page", required = false) Integer perPage,
            @RequestParam(name = "community_id", required = false) String communityId,
            @RequestParam(required = false, defaultValue = "hot") String sort
    ) {
        PaginationParams pagination = PaginationParams.from(page, perPage);
        return postService.listFeed(pagination.page(), pagination.perPage(), communityId, sort);
    }
}
