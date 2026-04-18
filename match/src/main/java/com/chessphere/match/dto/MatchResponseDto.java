package com.chessphere.match.dto;

import com.chessphere.match.enums.GameType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchResponseDto {
    private UUID id;
    private UUID whitePlayerId;
    private UUID blackPlayerId;
    private GameType gameType;
}
