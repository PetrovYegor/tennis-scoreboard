package com.github.petrovyegor.tennisscoreboard.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class MatchScore {
    private Map<Long, PlayerScore> playersScore;

    public MatchScore(long firstPlayerId, long secondPlayerId) {
        playersScore = new HashMap<>();
        playersScore.put(firstPlayerId, new PlayerScore(firstPlayerId));
        playersScore.put(secondPlayerId, new PlayerScore(secondPlayerId));
    }
}
