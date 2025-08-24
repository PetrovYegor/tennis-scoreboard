package com.github.petrovyegor.tennisscoreboard.model;

import lombok.Getter;

import java.util.UUID;

@Getter
public class OngoingMatch extends Match {
    private UUID uuid;
    private MatchScore matchScore;

    public OngoingMatch(UUID uuid, int firstPlayerId, int secondPlayerId) {
        super(firstPlayerId, secondPlayerId);
        this.uuid = uuid;
        matchScore = new MatchScore(firstPlayerId, secondPlayerId);
    }
}
