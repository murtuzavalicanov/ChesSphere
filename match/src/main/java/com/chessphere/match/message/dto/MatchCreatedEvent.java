package com.chessphere.match.message.dto;

import com.chessphere.match.enums.GameType;
import com.chessphere.match.enums.MatchStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MatchCreatedEvent {
    private UUID matchId;
    private UUID whitePlayerId;
    private UUID blackPlayerId;
    private GameType gameType;
    private MatchStatus matchStatus;
    private UUID senderId;
    private UUID recipientId;
}
