package com.chessphere.match.dto;

import com.chessphere.match.enums.GameType;
import com.chessphere.match.enums.MatchStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchRequestDto {
    private UUID id;
    private UUID whitePlayerId;
    private UUID blackPlayerId;
    private GameType gameType;
    private MatchStatus matchStatus;
}
