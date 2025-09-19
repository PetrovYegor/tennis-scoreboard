package com.github.petrovyegor.tennisscoreboard.dto;

import com.github.petrovyegor.tennisscoreboard.model.MatchStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class OngoingMatchDto {
    private UUID matchUuid;
    private PlayerScoreDto firstPlayerScore;
    private PlayerScoreDto secondPlayerScore;
    private MatchStatus matchStatus;

    public OngoingMatchDto(UUID matchUuid, PlayerScoreDto firstPlayerScore, PlayerScoreDto secondPlayerScore, MatchStatus matchStatus) {
        this.matchUuid = matchUuid;
        this.firstPlayerScore = firstPlayerScore;
        this.secondPlayerScore = secondPlayerScore;
        this.matchStatus = matchStatus;
    }
}

