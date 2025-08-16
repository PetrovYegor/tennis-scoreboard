package com.github.petrovyegor.tennisscoreboard.model;

public class PlayerScore {
    private int sets;//нужна валидация, что нельзя присвоить меньше нуля и что после 2 выигрывается
    private int games;
    private Point currentPoint;

    public PlayerScore() {
        currentPoint = Point.LOVE;
    }

    private void addPoint() {
        currentPoint = getNextPoint(currentPoint);
    }

    private void addGame() {
        games++;
    }

    private void addSet() {
        sets++;
    }

    private void checkScoreState() {
        if (isGameFinished()) {
            addGame();
            resetPoint();
        }
    }

    private void resetPoint() {
        currentPoint = Point.LOVE;
    }

    private boolean isGameFinished(){
        return currentPoint.equals(Point.LOVE);
    }

    private Point getNextPoint(Point currentPoint) {
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
