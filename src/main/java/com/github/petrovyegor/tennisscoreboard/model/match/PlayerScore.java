package com.github.petrovyegor.tennisscoreboard.model.match;

import lombok.Getter;

@Getter
public class PlayerScore {
    private final String playerName;
    private int sets;
    private int games;
    private Point point;
    private boolean advantage;
    private int tieBreakPoints;

    public PlayerScore(String name) {
        this.playerName = name;
        this.sets = 0;
        this.games = 0;
        this.point = Point.LOVE;
        this.advantage = false;
        this.tieBreakPoints = 0;
    }

    public void addTieBreakPoint() {
        this.tieBreakPoints++;
    }

    public void addPoint() {
        this.point = getNextRegularPoint();
    }

    public void setAdvantage() {
        this.advantage = true;
        this.point = Point.ADVANTAGE;
    }

    public void loseAdvantage() {
        this.advantage = false;
        this.point = Point.FORTY;
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
        this.games++;
    }

    public void winSet() {
        this.sets++;
    }

    public void resetAfterGame() {
        this.point = Point.LOVE;
        this.advantage = false;
    }

    public void resetAfterSet() {
        resetAfterGame();
        this.games = 0;
        this.tieBreakPoints = 0;
    }

    public String getFormattedRegularOrTieBreakPoint() {
        if (tieBreakPoints > 0) {
            return String.valueOf(tieBreakPoints);
        }
        return point.getValue();
    }

    private Point getNextRegularPoint() {
        return switch (point) {
            case LOVE -> Point.FIFTEEN;
            case FIFTEEN -> Point.THIRTY;
            case THIRTY -> Point.FORTY;
            case FORTY -> Point.ADVANTAGE;
            default -> throw new IllegalStateException("Unsupported Enum value given");
        };
    }
}