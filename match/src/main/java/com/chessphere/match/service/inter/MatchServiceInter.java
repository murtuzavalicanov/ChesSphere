package com.chessphere.match.service.inter;

import com.chessphere.match.entity.MatchEntity;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface MatchServiceInter {

    ResponseEntity<?> getUserMatches(UUID userId);

    void createMatch(MatchEntity match);

    void requestMatch(UUID userId, MatchEntity match);
}
