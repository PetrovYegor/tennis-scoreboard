package com.github.petrovyegor.tennisscoreboard.dto.match_score;

import com.github.petrovyegor.tennisscoreboard.model.Point;
import lombok.Data;

@Data
public class PlayerScoreDto {//TODO определиться с необходимостью этого класса, мб вместо него использовать модель PlayerScore
    private final long playerId;
    private final String playerName;
    private final int sets;
    private final int games;
    private final Point point;
    private final boolean advantage;
    private final int tieBreakPoints;
    private final String displayPoint;
}
