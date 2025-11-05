package com.github.petrovyegor.tennisscoreboard.dto.match_score;

import lombok.Data;

import java.util.UUID;

@Data
public class MatchScoreRequestDto {
    //TODO пересмотреть свои дтошки на предмет использования паттерна Builder
    private UUID matchUuid;
    private int roundWinnerId;

    public MatchScoreRequestDto(UUID matchUuid, int roundWinnerId) {
        this.matchUuid = matchUuid;
        this.roundWinnerId = roundWinnerId;
    }
}
