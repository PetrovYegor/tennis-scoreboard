package com.github.petrovyegor.tennisscoreboard.service;

import com.github.petrovyegor.tennisscoreboard.model.Point;

import java.util.HashMap;
import java.util.Map;

public class MatchScoreCalculationService {
    private final Map<Integer, Point> pointsByPlayers = new HashMap<>();
    //обновляет счёт в матче. Реализует логику подсчёта счёта матча по очкам/геймам/сетам
    private void addPont(int playerId) {
        //int playerId = player.getId();
        Point currentValue = pointsByPlayers.get(playerId);
        Point nextValue = getNextPoint(currentValue);
        pointsByPlayers.put(playerId, nextValue);
    }

    private Point getNextPoint(Point current) {
        Point nextValue = switch (current) {
            case LOVE -> Point.FIFTEEN;
            case FIFTEEN -> Point.THIRTY;
            case THIRTY -> Point.FORTY;
            case FORTY -> Point.ADVANTAGE;
            case ADVANTAGE -> Point.GAME;
            default -> throw new IllegalStateException("Unsupported Enum value given");
        };
        return nextValue;
    }

    private boolean isGameOver() {
        return pointsByPlayers.containsValue(Point.GAME);
    }

    private int getWinnerId() {
        int winnerId = 0;
        for (Map.Entry<Integer, Point> pair : pointsByPlayers.entrySet()) {
            if (pair.getValue().equals(Point.GAME)) {
                winnerId = pair.getKey();
            }
        }
        return winnerId;
    }
    private void resetPoints(){
        pointsByPlayers.replaceAll((k, v) -> Point.LOVE);
    }
}
