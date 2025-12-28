package com.github.petrovyegor.tennisscoreboard.dto.match.score;

import com.github.petrovyegor.tennisscoreboard.model.match.Point;

public record PlayerScoreDto(
        int sets,
        int games,
        Point point,
        boolean advantage,
        int tieBreakPoints,
        String displayPoint
) {}
