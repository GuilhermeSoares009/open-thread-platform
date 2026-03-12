package com.openthread.api.service;

import com.openthread.api.domain.CommunityEntity;
import com.openthread.api.domain.CommunityRepository;
import com.openthread.api.web.dto.CommunityDto;
import com.openthread.api.web.dto.CreateCommunityRequest;
import com.openthread.api.web.dto.PaginatedResponse;
import com.openthread.api.web.dto.PaginationMeta;
import com.openthread.api.web.error.BadRequestException;
import com.openthread.api.web.error.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final DtoMapper dtoMapper;

    public CommunityService(CommunityRepository communityRepository, DtoMapper dtoMapper) {
        this.communityRepository = communityRepository;
        this.dtoMapper = dtoMapper;
    }

    public PaginatedResponse<CommunityDto> list(int page, int perPage) {
        Page<CommunityEntity> communities = communityRepository.findAll(PageRequest.of(page - 1, perPage));
        List<CommunityDto> data = communities.stream().map(dtoMapper::toCommunityDto).toList();
        return new PaginatedResponse<>(data, new PaginationMeta(page, perPage, communities.getTotalElements()));
    }

    public CommunityDto get(String communityId) {
        java.util.UUID parsedCommunityId;
        try {
            parsedCommunityId = java.util.UUID.fromString(communityId);
        } catch (Exception exception) {
            throw new BadRequestException("Invalid communityId UUID");
        }

        CommunityEntity community = communityRepository.findById(parsedCommunityId)
                .orElseThrow(() -> new NotFoundException("Community not found"));
        return dtoMapper.toCommunityDto(community);
    }

    public CommunityDto create(CreateCommunityRequest request) {
        if (communityRepository.existsBySlugIgnoreCase(request.slug())) {
            throw new BadRequestException("Community slug already exists");
        }

        CommunityEntity community = new CommunityEntity();
        community.setName(request.name().trim());
        community.setSlug(request.slug().trim().toLowerCase());
        community.setDescription(request.description());

        communityRepository.save(community);
        return dtoMapper.toCommunityDto(community);
    }
}
