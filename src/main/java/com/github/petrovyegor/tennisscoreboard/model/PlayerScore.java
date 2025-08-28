package com.github.petrovyegor.tennisscoreboard.model;

import lombok.Getter;

@Getter
public class PlayerScore {
    private int sets;//нужна валидация, что нельзя присвоить меньше нуля и что после 2 выигрывается
    private int games;
    private Point currentPoint;

    public PlayerScore() {
        currentPoint = Point.LOVE;
    }

    private void addGame() {
        games++;
    }

    private void addSet() {
        sets++;
    }

    public void addPoint(){
        currentPoint = getNextPoint();
    }

    public void checkScoreState() {
        if (isGameFinished()) {
            addGame();
            resetPoint();
        }
    }

    public void resetPoint() {
        currentPoint = Point.LOVE;
    }

    public boolean isGameFinished(){
        return currentPoint.equals(Point.LOVE);
    }

    private Point getNextPoint() {
        Point nextValue = switch (currentPoint) {
            case LOVE -> Point.FIFTEEN;
            case FIFTEEN -> Point.THIRTY;
            case THIRTY -> Point.FORTY;
            case FORTY -> Point.ADVANTAGE;
            case ADVANTAGE -> Point.GAME;
            default -> throw new IllegalStateException("Unsupported Enum value given");
        };
        return nextValue;
    }


//    public List<Set> getSets() {
//        return sets;
//    }
//
//    public void setSets(List<Set> sets) {
//        this.sets = sets;
//    }
//
//    public List<Game> getGames() {
//        return games;
//    }
//
//    public void setGames(List<Game> games) {
//        this.games = games;
//    }
//
//    public Point getCurrentPoint() {
//        return currentPoint;
//    }
//
//    public void setCurrentPoint(Point currentPoint) {
//        this.currentPoint = currentPoint;
//    }
//
//    public PlayerScore getNewPlayerScore() {
//        return new PlayerScore();
//    }


}
