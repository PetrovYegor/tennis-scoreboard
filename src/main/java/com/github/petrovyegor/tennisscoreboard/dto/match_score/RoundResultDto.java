package com.github.petrovyegor.tennisscoreboard.dto.match_score;

import java.util.UUID;

public record RoundResultDto(
        UUID matchUuid,
        PlayerScoreDto firstPlayerScore,
        PlayerScoreDto secondPlayerScore
) {

    //private boolean isMatchFinished;
    //private String winnerName;

//    public RoundResultDto(UUID matchUuid, PlayerScoreDto firstPlayerScore, PlayerScoreDto secondPlayerScore) {
//        this.matchUuid = matchUuid;
//        this.firstPlayerScore = firstPlayerScore;
//        this.secondPlayerScore = secondPlayerScore;
//    }
}

