package com.github.petrovyegor.tennisscoreboard.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class MatchScoreRequestDto {
    private UUID matchUuid;
    private int firstPlayerId;
    private int secondPlayerId;
    private int roundWinnerId;

    public MatchScoreRequestDto(UUID matchUuid, int firstPlayerId, int secondPlayerId, int roundWinnerId) {
        this.matchUuid = matchUuid;
        this.firstPlayerId = firstPlayerId;
        this.secondPlayerId = secondPlayerId;
        this.roundWinnerId = roundWinnerId;
    }
}
