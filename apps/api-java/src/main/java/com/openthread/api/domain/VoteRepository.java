package com.openthread.api.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VoteRepository extends JpaRepository<VoteEntity, UUID> {
    Optional<VoteEntity> findByUserIdAndVotableTypeAndVotableId(UUID userId, VotableType votableType, UUID votableId);
}
