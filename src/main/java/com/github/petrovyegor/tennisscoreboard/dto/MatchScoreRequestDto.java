package com.github.petrovyegor.tennisscoreboard.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class MatchScoreRequestDto {
    private UUID matchUuid;
    private int playerId;

    public MatchScoreRequestDto(UUID matchUuid, int playerId){
        this.matchUuid = matchUuid;
        this.playerId = playerId;
    }
}
