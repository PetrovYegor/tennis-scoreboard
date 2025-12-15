package com.github.petrovyegor.tennisscoreboard.dto.match_score;

import com.github.petrovyegor.tennisscoreboard.model.Point;

public record PlayerScoreDto(
        int sets,
        int games,
        Point point,
        boolean advantage,
        int tieBreakPoints,
        String displayPoint
) {
}
