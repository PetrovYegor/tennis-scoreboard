package com.github.petrovyegor.tennisscoreboard;

import com.github.petrovyegor.tennisscoreboard.model.PlayerScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class MatchManagerTest {
    private MatchManager matchManager;

    @BeforeEach
    void setUp() {
        PlayerScore firstPlayerScore = new PlayerScore(1);
        PlayerScore secondPlayerScore = new PlayerScore(2);
        matchManager = new MatchManager(firstPlayerScore, secondPlayerScore, 6);
    }

    @Test
    void newMatchHaveNotWinner(){
        int player1Sets = matchManager.getFirstPlayerScore().getSets();
        int player2Sets = matchManager.getSecondPlayerScore().getSets();
        assertFalse(matchManager.isWinnerExists(), "При старте матча не должно быть победителя!");
    }
    @Test
    void playersScoreEqualsFortyGameNotOver(){

    }
}
