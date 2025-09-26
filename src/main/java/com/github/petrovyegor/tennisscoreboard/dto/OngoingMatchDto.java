package com.github.petrovyegor.tennisscoreboard.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class OngoingMatchDto {
    private UUID matchUuid;
    private PlayerScoreDto firstPlayerScore;
    private PlayerScoreDto secondPlayerScore;

    public OngoingMatchDto(UUID matchUuid, PlayerScoreDto firstPlayerScore, PlayerScoreDto secondPlayerScore) {
        this.matchUuid = matchUuid;
        this.firstPlayerScore = firstPlayerScore;
        this.secondPlayerScore = secondPlayerScore;
    }
}

