package com.github.petrovyegor.tennisscoreboard.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class MatchScore {
    private Map<Integer, PlayerScore> playersScore;

    public MatchScore(int firstPlayerId, int secondPlayerId) {
        playersScore = new HashMap<>();
        playersScore.put(firstPlayerId, new PlayerScore(firstPlayerId));
        playersScore.put(secondPlayerId, new PlayerScore(secondPlayerId));
    }
}
