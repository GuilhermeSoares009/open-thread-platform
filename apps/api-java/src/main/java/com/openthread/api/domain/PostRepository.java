package com.openthread.api.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<PostEntity, UUID> {
    Page<PostEntity> findByDeletedAtIsNull(Pageable pageable);

    Page<PostEntity> findByCommunityIdAndDeletedAtIsNull(UUID communityId, Pageable pageable);
}
