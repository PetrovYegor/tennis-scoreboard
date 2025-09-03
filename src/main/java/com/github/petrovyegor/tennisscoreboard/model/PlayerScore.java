package com.github.petrovyegor.tennisscoreboard.model;

import lombok.Getter;

@Getter
public class PlayerScore {
    private int playerId;
    private int sets;//нужна валидация, что нельзя присвоить меньше нуля и что после 2 выигрывается
    private int games;
    private Point point;
    private boolean hasAdvantage;
    private int tieBreakScore;

    public PlayerScore(int playerId) {
        this.playerId = playerId;
        point = Point.LOVE;
    }

    public boolean hasAdvantage() {
        return hasAdvantage;
    }

    public void assignGame() {
        point = Point.GAME;
    }

    public void addTieBreakPoint() {
        tieBreakScore++;
    }

    public void addGame() {
        games++;
    }

    public void addSet() {
        sets++;
    }

    public void addPoint() {
        point = getNextPoint();
    }

    public void setAdvantage() {
        hasAdvantage = true;
    }

    public void resetGames() {
        games = 0;
    }

    public void loseAdvantage() {
        hasAdvantage = false;
    }

    public void resetPoint() {
        point = Point.LOVE;
    }

    private Point getNextPoint() {
        Point nextValue = switch (point) {
            case LOVE -> Point.FIFTEEN;
            case FIFTEEN -> Point.THIRTY;
            case THIRTY -> Point.FORTY;
            case FORTY -> Point.ADVANTAGE;
            default -> throw new IllegalStateException("Unsupported Enum value given");
        };
        return nextValue;
    }

    public boolean isEqualsForty() {
        return point == Point.FORTY;
    }

    public boolean isFortyOrAd() {
        return point == Point.FORTY || point == Point.ADVANTAGE;
    }

    public boolean isEqualsGame() {
        return point == Point.GAME;
    }
}
