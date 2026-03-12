package com.openthread.api.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<CommentEntity, UUID> {
    Page<CommentEntity> findByPostIdAndDeletedAtIsNull(UUID postId, Pageable pageable);

    Optional<CommentEntity> findByIdAndDeletedAtIsNull(UUID id);
}
