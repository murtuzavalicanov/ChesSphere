package com.chessphere.match.controller;

import com.chessphere.match.entity.MatchEntity;
import com.chessphere.match.service.inter.MatchServiceInter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class MatchController {

    private final MatchServiceInter matchService;

    public MatchController(MatchServiceInter matchServiceInter) {
        this.matchService = matchServiceInter;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserMatch(@PathVariable("id") UUID userId) {
        return matchService.getUserMatches(userId);
    }

    @PostMapping("/create-match")
    public ResponseEntity<?> createMatch(@RequestBody MatchEntity matchEntity) {
        matchService.createMatch(matchEntity);
        return ResponseEntity.ok("Match created successfully");
    }

    @PostMapping("/request-match/{id}")
    public ResponseEntity<?> requestMatch(@PathVariable("id") UUID userId, @RequestBody MatchEntity requestedMatch) {
        matchService.requestMatch(userId, requestedMatch);
        return ResponseEntity.ok("Match request sent successfully");
    }
}
