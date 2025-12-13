package com.github.petrovyegor.tennisscoreboard.dto.ongoing_match;

import com.github.petrovyegor.tennisscoreboard.dto.match_score.PlayerScoreDto;
import lombok.Data;

import java.util.UUID;

@Data
public class OngoingMatchDto {
    private final UUID matchUuid;
    private final PlayerScoreDto firstPlayerScore;
    private final PlayerScoreDto secondPlayerScore;
    private boolean isMatchFinished;
    private String winnerName;

    public OngoingMatchDto(UUID matchUuid, PlayerScoreDto firstPlayerScore, PlayerScoreDto secondPlayerScore) {
        this.matchUuid = matchUuid;
        this.firstPlayerScore = firstPlayerScore;
        this.secondPlayerScore = secondPlayerScore;
    }
}

