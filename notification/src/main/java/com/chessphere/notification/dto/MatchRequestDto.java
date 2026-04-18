package com.chessphere.notification.dto;

import com.chessphere.notification.enums.GameType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchRequestDto {

    private UUID id;
    private UUID whitePlayerId;
    private UUID blackPlayerId;
    private GameType gameType;
}
