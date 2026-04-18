package com.chessphere.match.message.dto;

import com.chessphere.match.enums.GameType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MatchCreatedEvent {
    private UUID matchId;
    private UUID player1Id;
    private UUID player2Id;
    private GameType gameType;
}
