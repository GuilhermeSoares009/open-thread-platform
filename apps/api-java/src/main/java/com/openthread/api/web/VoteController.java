package com.openthread.api.web;

import com.openthread.api.service.AuthService;
import com.openthread.api.service.VoteService;
import com.openthread.api.web.dto.CreateVoteRequest;
import com.openthread.api.web.dto.VoteDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/votes")
public class VoteController {

    private final VoteService voteService;
    private final AuthService authService;

    public VoteController(VoteService voteService, AuthService authService) {
        this.voteService = voteService;
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<VoteDto> castVote(@Valid @RequestBody CreateVoteRequest request, HttpServletRequest httpRequest) {
        UUID userId = authService.requireUserId(httpRequest);
        VoteService.VoteResult result = voteService.castVote(userId, request);
        return ResponseEntity.status(result.created() ? HttpStatus.CREATED : HttpStatus.OK).body(result.vote());
    }
}
