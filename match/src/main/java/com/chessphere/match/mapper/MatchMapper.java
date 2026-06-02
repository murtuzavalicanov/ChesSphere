package com.chessphere.match.mapper;

import com.chessphere.match.dto.MatchRequestDto;
import com.chessphere.match.entity.MatchEntity;
import com.chessphere.match.message.dto.MatchCreatedEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MatchMapper {

    public MatchCreatedEvent toMatchCreatedEvent(UUID senderId, UUID recipientId, MatchRequestDto matchRequestDto) {
        if (matchRequestDto == null||senderId==null||recipientId==null) {
            return null;
        }
        return MatchCreatedEvent.builder()
                .matchId(matchRequestDto.getId())
                .whitePlayerId(matchRequestDto.getWhitePlayerId())
                .blackPlayerId(matchRequestDto.getBlackPlayerId())
                .matchStatus(matchRequestDto.getMatchStatus())
                .gameType(matchRequestDto.getGameType())
                .senderId(senderId)
                .recipientId(recipientId)
                .build();
    }

    public MatchRequestDto toMathcRequestedDto(UUID senderId, UUID recipientId, MatchEntity matchEntity) {
        if (matchEntity == null||senderId==null||recipientId==null) {
            return null;
        }
        return MatchRequestDto.builder()
                .id(matchEntity.getId())
                .whitePlayerId(matchEntity.getWhitePlayerId())
                .blackPlayerId(matchEntity.getBlackPlayerId())
                .gameType(matchEntity.getGameType())
                .matchStatus(matchEntity.getStatus())
                .build();
    }

    public MatchEntity toEntity(UUID senderId, UUID recipientId, MatchRequestDto requestedMatchDto) {
        if (requestedMatchDto == null||senderId==null||recipientId==null) {
            return null;
        }

        return MatchEntity.builder()
                .senderId(requestedMatchDto.getId())
                .whitePlayerId(requestedMatchDto.getWhitePlayerId())
                .blackPlayerId(requestedMatchDto.getBlackPlayerId())
                .gameType(requestedMatchDto.getGameType())
                .senderId(senderId)
                .recipientId(recipientId)
                .build();
    }
}
