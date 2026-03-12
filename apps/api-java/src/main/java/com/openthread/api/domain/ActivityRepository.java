package com.openthread.api.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ActivityRepository extends JpaRepository<ActivityEntity, UUID> {
    Page<ActivityEntity> findByUserIdOrderByCreatedAtDesc(UUID userId, Pageable pageable);
}
