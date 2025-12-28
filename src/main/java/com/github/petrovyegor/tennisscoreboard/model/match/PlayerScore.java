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
        tieBreakPoints++;
    }

    public void addPoint() {
        point = getNextRegularPoint();
    }

    public void setAdvantage() {
        advantage = true;
        point = Point.ADVANTAGE;
    }

    public void loseAdvantage() {
        advantage = false;
        point = Point.FORTY;
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
    }

    public void winSet() {
        sets++;
    }

    public void resetAfterGame() {
        point = Point.LOVE;
        advantage = false;
    }

    public void resetAfterSet() {
        resetAfterGame();
        games = 0;
        tieBreakPoints = 0;
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
