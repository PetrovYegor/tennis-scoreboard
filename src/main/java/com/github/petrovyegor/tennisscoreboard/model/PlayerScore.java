package com.github.petrovyegor.tennisscoreboard.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerScore {
//    private UUID id;
    private List<Set> sets;
    private List<Game> games;
    private Point currentPoint;

    public PlayerScore() {
        //id = UUID.randomUUID();
        sets = new ArrayList<>();
        games = new ArrayList<>();
        currentPoint = Point.LOVE;
    }

    private void addPoint() {
        currentPoint = getNextPoint(currentPoint);
    }

    private void addGame(UUID playerScoreId){
        games.add(new Game());
    }

    private void addSet(){
        sets.add(new Set());
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
