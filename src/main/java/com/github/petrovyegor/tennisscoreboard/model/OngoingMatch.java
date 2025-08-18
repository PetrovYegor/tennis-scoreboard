package com.github.petrovyegor.tennisscoreboard.model;

import java.util.UUID;

public class OngoingMatch extends Match {
    private UUID uuid;
    private MatchScore matchScore;

    public OngoingMatch(UUID uuid, int firstPlayerId, int secondPlayerId) {
        this.uuid = uuid;
        setFirstPlayerId(firstPlayerId);
        setSecondPlayerId(secondPlayerId);
        matchScore = new MatchScore(firstPlayerId, secondPlayerId);
    }

    public UUID getUuid() {
        return uuid;
    }
}
