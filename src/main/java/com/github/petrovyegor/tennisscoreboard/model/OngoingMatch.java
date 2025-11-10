package com.github.petrovyegor.tennisscoreboard.model;

import com.github.petrovyegor.tennisscoreboard.model.entity.Player;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class OngoingMatch {
    private final UUID uuid;
    private final Player firstPlayer;
    private final Player secondPlayer;
    private final Map<Long, PlayerScore> playersScore;//TODO это поле не должно быть доступно геттером

    public OngoingMatch(UUID uuid, Player firstPlayer, Player secondPlayer) {
        this.uuid = uuid;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.playersScore = new ConcurrentHashMap<>();
        initializePlayersScore(firstPlayer, secondPlayer);
    }

    public PlayerScore getFirstPlayerScore(){
        return playersScore.get(firstPlayer.getId());
    }

    public PlayerScore getSecondPlayerScore(){
        return playersScore.get(secondPlayer.getId());
    }

    private void initializePlayersScore(Player firstPlayer, Player secondPlayer) {
        playersScore.put(firstPlayer.getId(), new PlayerScore(firstPlayer.getName()));
        playersScore.put(secondPlayer.getId(), new PlayerScore(secondPlayer.getName()));
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
}
