package com.github.petrovyegor.tennisscoreboard.model;

import lombok.Getter;

@Getter
public class PlayerScore {
    private int playerId;
    private int sets;
    private int games;
    private Point point;
    private boolean advantage;
    private int tieBreakPoints;

    public PlayerScore(int playerId) {
        this.playerId = playerId;
        this.sets = 0;
        this.games = 0;
        this.point = Point.LOVE;
        this.advantage = false;
        this.tieBreakPoints = 0;
    }

    public void addTieBreakPoint() {
        tieBreakPoints++;
    }

    public void addPoint() {
        point = getNextPoint();
    }

    public void setAdvantage() {
        advantage = true;
    }

    public void loseAdvantage() {
        advantage = false;
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

    public boolean isUnderForty() {
        return point == Point.LOVE || point == Point.FIFTEEN || point == Point.THIRTY;
    }

    public boolean isEqualsForty() {
        return point == Point.FORTY;
    }

    public boolean isFortyOrAd() {
        return point == Point.FORTY || point == Point.ADVANTAGE;
    }

    public void winGame() {
        games++;
        resetAfterGame();
    }

    public void winSet() {
        sets++;
        resetAfterSet();
    }

    public void resetAfterGame() {
        point = Point.LOVE;
        advantage = false;
    }

    public void resetAfterSet(){
        resetAfterGame();
        games = 0;
    }

    public void resetTieBreakPoint(){
        tieBreakPoints = 0;
    }
}
