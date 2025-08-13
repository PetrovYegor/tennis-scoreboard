package com.github.petrovyegor.tennisscoreboard.model;

public class OngoingMatch {
    private Match match;
    private Point matchScore;

    public OngoingMatch(Match match, Point matchScore) {
        this.match = match;
        this.matchScore = matchScore;
    }
}
