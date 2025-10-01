package com.github.petrovyegor.tennisscoreboard.dto;

import lombok.Data;

@Data
public class ScoreCalculationResultDto {
    private OngoingMatchDto ongoingMatchDto;
    private boolean isWinnerExists;
    private int winnerId;
}
