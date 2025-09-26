package com.github.petrovyegor.tennisscoreboard;

import com.github.petrovyegor.tennisscoreboard.model.OngoingMatch;
import com.github.petrovyegor.tennisscoreboard.model.PlayerScore;
import com.github.petrovyegor.tennisscoreboard.model.Point;
import com.github.petrovyegor.tennisscoreboard.service.MatchScoreCalculationService;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchManagerTest {
    MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();

    //При старте игры у игроков счёт 0-0
    @Test
    public void testNewGameScoreLoveVSLove(){
        PlayerScore firstPlayerScore = new PlayerScore(0);
        PlayerScore secondPlayerScore = new PlayerScore(1);
        assertEquals(Point.LOVE, firstPlayerScore.getPoint());
        assertEquals(Point.LOVE, secondPlayerScore.getPoint());
    }

    //Если игрок 1 выигрывает очко при счёте 0-0, счёт становится 15-0
    @Test
    public void testFirstWonPointLeadsTo15vs0(){
        PlayerScore firstPlayerScore = new PlayerScore(0);
        PlayerScore secondPlayerScore = new PlayerScore(1);
        assertEquals(Point.FIFTEEN, firstPlayerScore.getPoint());
        assertEquals(Point.LOVE, secondPlayerScore.getPoint());
    }

    //Если игрок 1 выигрывает очко при счёте 15-0, счёт становится 30-0
    @Test
    public void testWonPointAt15vs0LeadsTo30vs0(){

    }


    //Если игрок 1 выигрывает очко при счёте 40-40, гейм не заканчивается
    @Test
    public void testGameNotOverWhenFortyVSForty() {
        PlayerScore firstPlayerScore = new PlayerScore(0);
        PlayerScore secondPlayerScore = new PlayerScore(1);
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);

        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);

        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);

        assertEquals(0, firstPlayerScore.getGames());
        assertEquals(0, secondPlayerScore.getGames());
    }

    // Если игрок 1 выигрывает очко при счёте 40-0, то он выигрывает и гейм
    @Test
    public void testGameOverWhenFortyVSLove() {
        PlayerScore firstPlayerScore = new PlayerScore(0);
        PlayerScore secondPlayerScore = new PlayerScore(1);
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);

        assertEquals(1, firstPlayerScore.getGames());
        assertEquals(0, secondPlayerScore.getGames());
    }

    //При счёте 6-6 начинается тайбрейк вместо обычного гейма
    @Test
    public void testTieBreakStartWhen6GamesVS6Games() {
        PlayerScore firstPlayerScore = new PlayerScore(0);
        PlayerScore secondPlayerScore = new PlayerScore(1);

        //выигрываем 5 геймов игроку1
        for (int i = 0; i < 20; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        }

        //выигрываем 5 геймов игроку2
        for (int i = 0; i < 20; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);
        }
        //выигрываем ещё по одному гейму каждому игроку, чтобы счё по геймам стал 6 - 6
        for (int i = 0; i < 4; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        }
        for (int i = 0; i < 4; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);
        }
        //выигрываем очко при счёте по геймам 6 - 6 -> добавляется очко в поле tieBreakPoint
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        assertEquals(1, firstPlayerScore.getTieBreakPoints());
    }

//    private MatchManager matchManager;
//
//    @BeforeEach
//    void setUp() {
//        PlayerScore firstPlayerScore = new PlayerScore(1);
//        PlayerScore secondPlayerScore = new PlayerScore(2);
//        matchManager = new MatchManager(firstPlayerScore, secondPlayerScore, 6);
//    }
//
//    @Test
//    void newMatchHaveNotWinner(){
//        int player1Sets = matchManager.getFirstPlayerScore().getSets();
//        int player2Sets = matchManager.getSecondPlayerScore().getSets();
//        assertFalse(matchManager.isWinnerExists(), "При старте матча не должно быть победителя!");
//    }
//    @Test
//    void playersScoreEqualsFortyGameNotOver(){
//
//    }
}
