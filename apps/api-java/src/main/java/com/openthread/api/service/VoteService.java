package com.openthread.api.service;

import com.openthread.api.domain.ActivityType;
import com.openthread.api.domain.VotableType;
import com.openthread.api.domain.VoteEntity;
import com.openthread.api.domain.VoteRepository;
import com.openthread.api.web.dto.CreateVoteRequest;
import com.openthread.api.web.dto.VoteDto;
import com.openthread.api.web.error.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostService postService;
    private final ActivityService activityService;
    private final DomainEventPublisher eventPublisher;
    private final DtoMapper dtoMapper;

    public VoteService(
            VoteRepository voteRepository,
            PostService postService,
            ActivityService activityService,
            DomainEventPublisher eventPublisher,
            DtoMapper dtoMapper
    ) {
        this.voteRepository = voteRepository;
        this.postService = postService;
        this.activityService = activityService;
        this.eventPublisher = eventPublisher;
        this.dtoMapper = dtoMapper;
    }

    @Transactional
    public VoteResult castVote(UUID userId, CreateVoteRequest request) {
        VotableType votableType = parseType(request.votableType());

        if (request.value() != -1 && request.value() != 1) {
            throw new BadRequestException("Vote value must be -1 or 1");
        }

        VoteEntity vote = voteRepository.findByUserIdAndVotableTypeAndVotableId(userId, votableType, request.votableId())
                .orElse(null);

        boolean created = vote == null;
        int previousValue = 0;
        if (vote == null) {
            vote = new VoteEntity();
            vote.setUserId(userId);
            vote.setVotableType(votableType);
            vote.setVotableId(request.votableId());
        } else {
            previousValue = vote.getValue();
        }

        vote.setValue(request.value());
        voteRepository.save(vote);

        int delta = request.value() - previousValue;
        if (delta != 0) {
            if (votableType == VotableType.POST) {
                postService.incrementPostScore(request.votableId(), delta);
            } else {
                postService.incrementCommentScore(request.votableId(), delta);
            }
        }

        activityService.log(userId, ActivityType.VOTE, Map.of(
                "votable_type", votableType.name().toLowerCase(),
                "votable_id", request.votableId().toString(),
                "value", request.value()
        ));

        eventPublisher.publish("vote.cast", Map.of(
                "user_id", userId.toString(),
                "votable_type", votableType.name().toLowerCase(),
                "votable_id", request.votableId().toString(),
                "value", request.value(),
                "delta", delta
        ));

        return new VoteResult(dtoMapper.toVoteDto(vote), created);
    }

    private VotableType parseType(String value) {
        if (value == null) {
            throw new BadRequestException("votable_type is required");
        }
        return switch (value.toLowerCase()) {
            case "post" -> VotableType.POST;
            case "comment" -> VotableType.COMMENT;
            default -> throw new BadRequestException("Invalid votable_type");
        };
    }

    public record VoteResult(VoteDto vote, boolean created) {
    }
}
