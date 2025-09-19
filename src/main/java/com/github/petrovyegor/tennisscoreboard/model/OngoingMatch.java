package com.github.petrovyegor.tennisscoreboard.model;

import com.github.petrovyegor.tennisscoreboard.model.entity.Player;
import lombok.Getter;

import java.util.UUID;

@Getter
public class OngoingMatch {
    private UUID uuid;
    private Player firstPlayer;
    private Player secondPlayer;
    private MatchScore matchScore;
    private MatchStatus matchStatus;

    public OngoingMatch(UUID uuid, Player firstPlayer, Player secondPlayer) {
        this.uuid = uuid;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.matchScore = new MatchScore(firstPlayer.getId(), secondPlayer.getId());
        this.matchStatus = MatchStatus.IN_PROGRESS;
    }

    public PlayerScore getPlayerScore(Player player) {
        return matchScore.getPlayersScore().get(player.getId());
    }
}
