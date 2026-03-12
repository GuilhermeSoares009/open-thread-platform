package com.openthread.api.service;

import com.openthread.api.domain.CommentEntity;
import com.openthread.api.domain.CommunityEntity;
import com.openthread.api.domain.PostEntity;
import com.openthread.api.domain.UserEntity;
import com.openthread.api.domain.VoteEntity;
import com.openthread.api.web.dto.CommentDto;
import com.openthread.api.web.dto.CommunityDto;
import com.openthread.api.web.dto.PostDto;
import com.openthread.api.web.dto.UserDto;
import com.openthread.api.web.dto.VoteDto;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {

    public DtoMapper() {
    }

    public CommunityDto toCommunityDto(CommunityEntity entity) {
        return new CommunityDto(
                entity.getId().toString(),
                entity.getName(),
                entity.getSlug(),
                entity.getDescription(),
                entity.getCreatedAt()
        );
    }

    public PostDto toPostDto(PostEntity entity) {
        return new PostDto(
                entity.getId().toString(),
                entity.getCommunityId().toString(),
                entity.getUserId().toString(),
                entity.getTitle(),
                entity.getBody(),
                entity.getScore(),
                entity.getCommentCount(),
                entity.getCreatedAt()
        );
    }

    public CommentDto toCommentDto(CommentEntity entity) {
        return new CommentDto(
                entity.getId().toString(),
                entity.getPostId().toString(),
                entity.getUserId().toString(),
                entity.getParentId() == null ? null : entity.getParentId().toString(),
                entity.getDepth(),
                entity.getBody(),
                entity.getScore(),
                entity.getCreatedAt()
        );
    }

    public VoteDto toVoteDto(VoteEntity entity) {
        return new VoteDto(
                entity.getId().toString(),
                entity.getUserId().toString(),
                entity.getVotableType().name().toLowerCase(),
                entity.getVotableId().toString(),
                entity.getValue()
        );
    }

    public UserDto toUserDto(UserEntity entity) {
        return new UserDto(
                entity.getId().toString(),
                entity.getName(),
                entity.getUsername(),
                entity.getAvatarUrl(),
                entity.getBio()
        );
    }
}
