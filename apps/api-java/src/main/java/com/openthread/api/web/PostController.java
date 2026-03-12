package com.openthread.api.web;

import com.openthread.api.service.AuthService;
import com.openthread.api.service.PostService;
import com.openthread.api.web.dto.CommentDto;
import com.openthread.api.web.dto.CreateCommentRequest;
import com.openthread.api.web.dto.CreatePostRequest;
import com.openthread.api.web.dto.PaginatedResponse;
import com.openthread.api.web.dto.PostDto;
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

import java.util.UUID;

@RestController
public class PostController {

    private final PostService postService;
    private final AuthService authService;

    public PostController(PostService postService, AuthService authService) {
        this.postService = postService;
        this.authService = authService;
    }

    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public PostDto createPost(@Valid @RequestBody CreatePostRequest request, HttpServletRequest httpRequest) {
        UUID userId = authService.requireUserId(httpRequest);
        return postService.createPost(userId, request);
    }

    @GetMapping("/posts/{postId}")
    public PostDto getPost(@PathVariable String postId) {
        return postService.getPost(postId);
    }

    @GetMapping("/posts/{postId}/comments")
    public PaginatedResponse<CommentDto> listComments(
            @PathVariable String postId,
            @RequestParam(required = false) Integer page,
            @RequestParam(name = "per_page", required = false) Integer perPage
    ) {
        PaginationParams pagination = PaginationParams.from(page, perPage);
        return postService.listComments(postId, pagination.page(), pagination.perPage());
    }

    @PostMapping("/posts/{postId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createComment(
            @PathVariable String postId,
            @Valid @RequestBody CreateCommentRequest request,
            HttpServletRequest httpRequest
    ) {
        UUID userId = authService.requireUserId(httpRequest);
        return postService.createComment(userId, postId, request);
    }
}
