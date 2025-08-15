package com.github.petrovyegor.tennisscoreboard.model;

import java.util.HashMap;
import java.util.Map;

public class MatchScore {
    private Map<Integer, PlayerScore> playersScore;

    public MatchScore(int firstPlayerId, int secondPlayerId) {
        playersScore = new HashMap<>();
        playersScore.put(firstPlayerId, new PlayerScore());
        playersScore.put(secondPlayerId, new PlayerScore());
    }
}
