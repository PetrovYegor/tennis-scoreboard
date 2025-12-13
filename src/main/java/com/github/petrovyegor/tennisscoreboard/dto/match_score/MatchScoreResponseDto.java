package com.github.petrovyegor.tennisscoreboard.dto.match_score;

import lombok.Data;

import java.util.UUID;

@Data
public class MatchScoreResponseDto {
    private final UUID matchUuid;
    private final PlayerScoreDto firstPlayerScore;
    private final PlayerScoreDto secondPlayerScore;
    private boolean isMatchFinished;
    private String winnerName;

    public MatchScoreResponseDto(UUID matchUuid, PlayerScoreDto firstPlayerScore, PlayerScoreDto secondPlayerScore) {
        this.matchUuid = matchUuid;
        this.firstPlayerScore = firstPlayerScore;
        this.secondPlayerScore = secondPlayerScore;
    }
}

