package com.openthread.api.service;

import com.openthread.api.domain.ActivityType;
import com.openthread.api.domain.CommentEntity;
import com.openthread.api.domain.CommentRepository;
import com.openthread.api.domain.CommunityRepository;
import com.openthread.api.domain.PostEntity;
import com.openthread.api.domain.PostRepository;
import com.openthread.api.web.dto.CommentDto;
import com.openthread.api.web.dto.CreateCommentRequest;
import com.openthread.api.web.dto.CreatePostRequest;
import com.openthread.api.web.dto.PaginatedResponse;
import com.openthread.api.web.dto.PaginationMeta;
import com.openthread.api.web.dto.PostDto;
import com.openthread.api.web.error.BadRequestException;
import com.openthread.api.web.error.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommunityRepository communityRepository;
    private final CommentRepository commentRepository;
    private final ActivityService activityService;
    private final DomainEventPublisher eventPublisher;
    private final DtoMapper dtoMapper;

    @Value("${openthread.comments.max-depth:6}")
    private int maxCommentDepth;

    public PostService(
            PostRepository postRepository,
            CommunityRepository communityRepository,
            CommentRepository commentRepository,
            ActivityService activityService,
            DomainEventPublisher eventPublisher,
            DtoMapper dtoMapper
    ) {
        this.postRepository = postRepository;
        this.communityRepository = communityRepository;
        this.commentRepository = commentRepository;
        this.activityService = activityService;
        this.eventPublisher = eventPublisher;
        this.dtoMapper = dtoMapper;
    }

    public PaginatedResponse<PostDto> listFeed(int page, int perPage, String communityId, String sort) {
        Sort sorting = switch (sort == null ? "hot" : sort) {
            case "new" -> Sort.by(Sort.Order.desc("createdAt"));
            case "top" -> Sort.by(Sort.Order.desc("score"), Sort.Order.desc("createdAt"));
            case "hot" -> Sort.by(Sort.Order.desc("score"), Sort.Order.desc("createdAt"));
            default -> throw new BadRequestException("Invalid sort value");
        };

        Page<PostEntity> posts;
        if (communityId == null || communityId.isBlank()) {
            posts = postRepository.findByDeletedAtIsNull(PageRequest.of(page - 1, perPage, sorting));
        } else {
            posts = postRepository.findByCommunityIdAndDeletedAtIsNull(parseUuid(communityId, "community_id"), PageRequest.of(page - 1, perPage, sorting));
        }

        return new PaginatedResponse<>(
                posts.stream().map(dtoMapper::toPostDto).toList(),
                new PaginationMeta(page, perPage, posts.getTotalElements())
        );
    }

    public PostDto getPost(String postId) {
        PostEntity post = postRepository.findById(parseUuid(postId, "postId"))
                .filter(value -> value.getDeletedAt() == null)
                .orElseThrow(() -> new NotFoundException("Post not found"));

        return dtoMapper.toPostDto(post);
    }

    @Transactional
    public PostDto createPost(UUID userId, CreatePostRequest request) {
        if (!communityRepository.existsById(request.communityId())) {
            throw new NotFoundException("Community not found");
        }

        PostEntity post = new PostEntity();
        post.setCommunityId(request.communityId());
        post.setUserId(userId);
        post.setTitle(request.title().trim());
        post.setBody(request.body().trim());
        post.setScore(0);
        post.setCommentCount(0);

        postRepository.save(post);

        activityService.log(userId, ActivityType.POST, Map.of(
                "post_id", post.getId().toString(),
                "community_id", post.getCommunityId().toString()
        ));

        eventPublisher.publish("post.created", Map.of(
                "post_id", post.getId().toString(),
                "community_id", post.getCommunityId().toString(),
                "user_id", userId.toString()
        ));

        return dtoMapper.toPostDto(post);
    }

    public PaginatedResponse<CommentDto> listComments(String postId, int page, int perPage) {
        UUID parsedPostId = parseUuid(postId, "postId");
        if (!postRepository.existsById(parsedPostId)) {
            throw new NotFoundException("Post not found");
        }

        Page<CommentEntity> comments = commentRepository.findByPostIdAndDeletedAtIsNull(parsedPostId, PageRequest.of(page - 1, perPage, Sort.by("createdAt")));
        return new PaginatedResponse<>(
                comments.stream().map(dtoMapper::toCommentDto).toList(),
                new PaginationMeta(page, perPage, comments.getTotalElements())
        );
    }

    @Transactional
    public CommentDto createComment(UUID userId, String postId, CreateCommentRequest request) {
        UUID parsedPostId = parseUuid(postId, "postId");
        PostEntity post = postRepository.findById(parsedPostId)
                .filter(value -> value.getDeletedAt() == null)
                .orElseThrow(() -> new NotFoundException("Post not found"));

        int depth = 0;
        if (request.parentId() != null) {
            CommentEntity parent = commentRepository.findByIdAndDeletedAtIsNull(request.parentId())
                    .orElseThrow(() -> new NotFoundException("Parent comment not found"));

            if (!parent.getPostId().equals(parsedPostId)) {
                throw new BadRequestException("Parent comment does not belong to this post");
            }

            depth = parent.getDepth() + 1;
        }

        if (depth > maxCommentDepth) {
            throw new BadRequestException("Maximum comment depth exceeded");
        }

        CommentEntity comment = new CommentEntity();
        comment.setPostId(parsedPostId);
        comment.setUserId(userId);
        comment.setParentId(request.parentId());
        comment.setDepth(depth);
        comment.setBody(request.body().trim());
        comment.setScore(0);

        commentRepository.save(comment);

        post.setCommentCount(post.getCommentCount() + 1);
        postRepository.save(post);

        activityService.log(userId, ActivityType.COMMENT, Map.of(
                "comment_id", comment.getId().toString(),
                "post_id", post.getId().toString(),
                "parent_id", comment.getParentId() == null ? "" : comment.getParentId().toString()
        ));

        eventPublisher.publish("comment.created", Map.of(
                "comment_id", comment.getId().toString(),
                "post_id", post.getId().toString(),
                "user_id", userId.toString(),
                "depth", comment.getDepth()
        ));

        return dtoMapper.toCommentDto(comment);
    }

    @Transactional
    public void incrementPostScore(UUID postId, int delta) {
        PostEntity post = postRepository.findById(postId)
                .filter(value -> value.getDeletedAt() == null)
                .orElseThrow(() -> new NotFoundException("Post not found"));
        post.setScore(post.getScore() + delta);
        postRepository.save(post);
    }

    @Transactional
    public void incrementCommentScore(UUID commentId, int delta) {
        CommentEntity comment = commentRepository.findByIdAndDeletedAtIsNull(commentId)
                .orElseThrow(() -> new NotFoundException("Comment not found"));
        comment.setScore(comment.getScore() + delta);
        commentRepository.save(comment);
    }

    private UUID parseUuid(String value, String field) {
        try {
            return UUID.fromString(value);
        } catch (Exception exception) {
            throw new BadRequestException("Invalid " + field + " UUID");
        }
    }
}
