package com.github.petrovyegor.tennisscoreboard.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class OngoingMatchDto {
    private UUID matchUuid;
    private PlayerScoreDto firstPlayerScore;
    private PlayerScoreDto secondPlayerScore;
    private boolean isMatchFinished;

    public OngoingMatchDto(UUID matchUuid, PlayerScoreDto firstPlayerScore, PlayerScoreDto secondPlayerScore, boolean isMatchFinished) {
        this.matchUuid = matchUuid;
        this.firstPlayerScore = firstPlayerScore;
        this.secondPlayerScore = secondPlayerScore;
        this.isMatchFinished = isMatchFinished;
    }
}

