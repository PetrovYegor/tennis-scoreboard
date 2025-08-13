package com.github.petrovyegor.tennisscoreboard.model;

import java.util.HashMap;

public class Game {
    private HashMap<Integer, Point> scoreByPlayers = new HashMap<>();

    public Game(Match match){
        scoreByPlayers.put(match.getFirstPlayerId(), Point.LOVE);
        scoreByPlayers.put(match.getSecondPlayerId(), Point.LOVE);
    }

    private void addPont(Player player){

    }

    private Point getNextPoint(Point current){

    }
}
