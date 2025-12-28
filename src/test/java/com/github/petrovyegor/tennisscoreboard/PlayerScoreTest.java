package com.github.petrovyegor.tennisscoreboard;

import com.github.petrovyegor.tennisscoreboard.model.match.PlayerScore;
import com.github.petrovyegor.tennisscoreboard.model.match.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerScoreTest {

    @Test
    public void testGameWinIncrementsGamesCounter() {
        PlayerScore firstPlayerScore = new PlayerScore("John");
        firstPlayerScore.winGame();
        assertEquals(1, firstPlayerScore.getGames());
    }

    @Test
    public void testSetWinIncrementsSetsCounter() {
        PlayerScore firstPlayerScore = new PlayerScore("John");
        firstPlayerScore.winSet();
        assertEquals(1, firstPlayerScore.getSets());
    }

    @Test
    public void testTieBreakPointWinIncrementsCounter() {
        PlayerScore firstPlayerScore = new PlayerScore("John");
        firstPlayerScore.addTieBreakPoint();
        assertEquals(1, firstPlayerScore.getTieBreakPoints());
    }

    @Test
    public void testSettingAdvantage() {
        PlayerScore firstPlayerScore = new PlayerScore("John");
        firstPlayerScore.setAdvantage();
        assertTrue(firstPlayerScore.isAdvantage());
    }

    @Test
    public void testLosingAdvantage() {
        PlayerScore firstPlayerScore = new PlayerScore("John");
        firstPlayerScore.setAdvantage();
        firstPlayerScore.loseAdvantage();
        assertFalse(firstPlayerScore.isAdvantage());
    }

    @Test
    public void testGettingNextRegularPoint() {
        PlayerScore firstPlayerScore = new PlayerScore("John");
        firstPlayerScore.addPoint();
        assertEquals(Point.FIFTEEN, firstPlayerScore.getPoint());
    }

    @Test
    public void testResetPointAfterGame(){
        PlayerScore firstPlayerScore = new PlayerScore("John");
        firstPlayerScore.addPoint();
        firstPlayerScore.resetAfterGame();
        assertEquals(Point.LOVE, firstPlayerScore.getPoint());
    }

    @Test
    public void testResetAdvantageAfterGame(){
        PlayerScore firstPlayerScore = new PlayerScore("John");
        firstPlayerScore.setAdvantage();
        firstPlayerScore.resetAfterGame();
        assertFalse(firstPlayerScore.isAdvantage());
    }

    @Test
    public void testResetTiBreakPointsAfterSet(){
        PlayerScore firstPlayerScore = new PlayerScore("John");
        firstPlayerScore.addTieBreakPoint();
        firstPlayerScore.resetAfterSet();
        assertEquals(0, firstPlayerScore.getTieBreakPoints());
    }

    @Test
    public void testResetGamesAfterSet(){
        PlayerScore firstPlayerScore = new PlayerScore("John");
        firstPlayerScore.winGame();
        firstPlayerScore.resetAfterSet();
        assertEquals(0, firstPlayerScore.getGames());
    }

    @Test
    public void testResetPointAfterSet(){
        PlayerScore firstPlayerScore = new PlayerScore("John");
        firstPlayerScore.addPoint();
        firstPlayerScore.resetAfterSet();
        assertEquals(Point.LOVE, firstPlayerScore.getPoint());
    }

    @Test
    public void testResetAdvantageAfterSet(){
        PlayerScore firstPlayerScore = new PlayerScore("John");
        firstPlayerScore.setAdvantage();
        firstPlayerScore.resetAfterSet();
        assertFalse(firstPlayerScore.isAdvantage());
    }
}
