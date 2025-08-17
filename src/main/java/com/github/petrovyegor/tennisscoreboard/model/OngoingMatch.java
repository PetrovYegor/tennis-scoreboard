package com.github.petrovyegor.tennisscoreboard.model;

public class OngoingMatch extends Match {
    private MatchScore matchScore;

    public OngoingMatch(int firstPlayerId, int secondPlayerId) {
        setFirstPlayerId(firstPlayerId);
        setSecondPlayerId(secondPlayerId);
        matchScore = new MatchScore(firstPlayerId, secondPlayerId);
    }
}
