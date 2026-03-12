package com.openthread.api.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommunityRepository extends JpaRepository<CommunityEntity, UUID> {
    boolean existsBySlugIgnoreCase(String slug);
}
