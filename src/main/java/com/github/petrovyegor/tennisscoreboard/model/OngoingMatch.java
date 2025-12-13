package com.github.petrovyegor.tennisscoreboard.model;

import com.github.petrovyegor.tennisscoreboard.model.entity.Player;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatch {
    @Getter
    private final UUID uuid;
    @Getter
    private final Player firstPlayer;
    @Getter
    private final Player secondPlayer;
    private final Map<Long, PlayerScore> playersScore;

    public OngoingMatch(UUID uuid, Player firstPlayer, Player secondPlayer) {
        this.uuid = uuid;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.playersScore = new ConcurrentHashMap<>();
        initializePlayersScore(firstPlayer, secondPlayer);
    }

    public PlayerScore getFirstPlayerScore() {
        return playersScore.get(firstPlayer.getId());
    }

    public PlayerScore getSecondPlayerScore() {
        return playersScore.get(secondPlayer.getId());
    }

    public PlayerScore getWinnerScore(int pointWinnerId) {
        if (pointWinnerId == firstPlayer.getId()) {
            return getFirstPlayerScore();
        }
        return getSecondPlayerScore();
    }

    public PlayerScore getOpponentScore(int pointWinnerId) {
        if (pointWinnerId == firstPlayer.getId()) {
            return getSecondPlayerScore();
        }
        return getFirstPlayerScore();
    }

    private void initializePlayersScore(Player firstPlayer, Player secondPlayer) {
        playersScore.put(firstPlayer.getId(), new PlayerScore(firstPlayer.getName()));
        playersScore.put(secondPlayer.getId(), new PlayerScore(secondPlayer.getName()));
    }
}
