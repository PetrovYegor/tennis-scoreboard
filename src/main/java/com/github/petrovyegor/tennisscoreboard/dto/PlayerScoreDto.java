package com.github.petrovyegor.tennisscoreboard.dto;

import com.github.petrovyegor.tennisscoreboard.model.Point;
import lombok.Data;

@Data
public class PlayerScoreDto {
    private final int playerId;
    private final String playerName;
    private final int sets;
    private final int games;
    private final Point point;
    private final boolean advantage;
    private final int tieBreakPoints;
    private final String displayPoint;
}
