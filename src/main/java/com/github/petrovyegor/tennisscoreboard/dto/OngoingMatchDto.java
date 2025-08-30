package com.github.petrovyegor.tennisscoreboard.dto;

import com.github.petrovyegor.tennisscoreboard.model.Point;
import lombok.Data;

@Data
public class OngoingMatchDto {
    private int firstPlayerId;
    private int secondPlayerId;
    private String firstPlayerName;
    private String secondPlayerName;
    private int firstPlayerSets;
    private int secondPlayerSets;
    private int firstPlayerGames;
    private int secondPlayerGames;
    private Point firstPlayerPoint;
    private Point secondPlayerPoint;
    private boolean hasAdvantageFirstPlayer;
    private boolean hasAdvantageSecondPlayer;

    public OngoingMatchDto(int firstPlayerId, int secondPlayerId, String firstPlayerName, String secondPlayerName, int firstPlayerSets
            , int secondPlayerSets, int firstPlayerGames, int secondPlayerGames
            , Point firstPlayerPoint, Point secondPlayerPoint, boolean hasAdvantageFirstPlayer, boolean hasAdvantageSecondPlayer) {
        this.firstPlayerId = firstPlayerId;
        this.secondPlayerId = secondPlayerId;
        this.firstPlayerName = firstPlayerName;
        this.secondPlayerName = secondPlayerName;
        this.firstPlayerSets = firstPlayerSets;
        this.secondPlayerSets = secondPlayerSets;
        this.firstPlayerGames = firstPlayerGames;
        this.secondPlayerGames = secondPlayerGames;
        this.firstPlayerPoint = firstPlayerPoint;
        this.secondPlayerPoint = secondPlayerPoint;
        this.hasAdvantageFirstPlayer = hasAdvantageFirstPlayer;
        this.hasAdvantageSecondPlayer = hasAdvantageSecondPlayer;
    }
}
