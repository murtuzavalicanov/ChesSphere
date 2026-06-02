package com.chessphere.match.service.impl;

import com.chessphere.match.dto.MatchRequestDto;
import com.chessphere.match.entity.MatchEntity;
import com.chessphere.match.mapper.MatchMapper;
import com.chessphere.match.message.dto.MatchCreatedEvent;
import com.chessphere.match.message.producer.MatchProducer;
import com.chessphere.match.repository.MatchRepo;
import com.chessphere.match.service.inter.MatchServiceInter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatchService implements MatchServiceInter {

   private final MatchRepo matchRepo;
   private final MatchProducer matchProducer;
   private final MatchMapper matchMapper;

    @Override
    public ResponseEntity<?> getUserMatches(UUID userId) {
        log.info("ActionLog.getUserMatches.started.userId: " + userId);
        List<MatchEntity> matches= matchRepo.findAllMatchByUserId(userId);
        if (matches.get(0)==null) {
            return ResponseEntity.ok("No matches found");
        }
        log.info("ActionLog.getUserMatches.started.matchers: " + matches.toString());
        return ResponseEntity.ok(matches);
    }

    @Override
    public void createMatch(MatchEntity match) {
        log.info("ActionLog.createMatch.started.match: " + match.toString());
        matchRepo.save(match);
        log.info("ActionLog.createMatch.saved.match: " + match);
    }

    @Override
    public void requestMatch(UUID senderId, UUID recipientId, MatchRequestDto requestedMatchDto) {
        log.info("ActionLog.requestMatch.started.sender: "+ senderId
                + " recipient: " + recipientId
                + " match: " + requestedMatchDto.toString());
        MatchEntity matchEntity = matchMapper.toEntity(senderId, recipientId, requestedMatchDto);
        createMatch(matchEntity);

        MatchCreatedEvent matchCreatedEvent = matchMapper.toMatchCreatedEvent(senderId, recipientId, requestedMatchDto);
        matchProducer.sendMatchCreatedEvent(matchCreatedEvent);
        log.info("ActionLog.requestMatch.saved.match: " + matchEntity.toString());
    }
}
