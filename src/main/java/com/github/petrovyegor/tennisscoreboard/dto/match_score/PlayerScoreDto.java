package com.github.petrovyegor.tennisscoreboard.dto.match_score;

import com.github.petrovyegor.tennisscoreboard.model.Point;
import lombok.Data;

public record PlayerScoreDto (
        long playerId,
        String playerName,
        int sets,
        int games,
        Point point,
        boolean advantage,
        int tieBreakPoints,
        String displayPoint
) {}
