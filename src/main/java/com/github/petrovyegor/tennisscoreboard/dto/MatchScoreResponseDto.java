package com.github.petrovyegor.tennisscoreboard.dto;

import lombok.Data;

@Data
public class MatchScoreResponseDto {
    private final OngoingMatchDto ongoingMatchDto;
    private final boolean isMatchFinished;
    private final String winnerName;
}
