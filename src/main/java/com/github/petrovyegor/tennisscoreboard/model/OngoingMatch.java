package com.github.petrovyegor.tennisscoreboard.model;

public class OngoingMatch {
    private int firstPlayerId;
    private int secondPlayerId;
    private MatchScore matchScore;

    public OngoingMatch(int firstPlayerId, int secondPlayerId){
        this.firstPlayerId = firstPlayerId;
        this.secondPlayerId = secondPlayerId;
        matchScore = new MatchScore(firstPlayerId, secondPlayerId);
    }
}
