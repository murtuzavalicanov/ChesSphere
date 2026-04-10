package com.chessphere.match.entity;

import com.chessphere.match.enums.GameType;
import com.chessphere.match.enums.MatchResult;
import com.chessphere.match.enums.MatchStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class MatchEntity {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private UUID whitePlayerId;
    private UUID blackPlayerId;

    @Enumerated(EnumType.STRING)
    private GameType gameType;
    @Enumerated(EnumType.STRING)
    private MatchResult result;
    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    private String moves;
    private LocalDateTime playedAt;


    @PrePersist
    public void prePersist() {
        this.status = MatchStatus.REQUESTED;
        this.playedAt = LocalDateTime.now();
    }
}
