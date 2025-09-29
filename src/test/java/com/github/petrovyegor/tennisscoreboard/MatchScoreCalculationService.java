package com.github.petrovyegor.tennisscoreboard;

import com.github.petrovyegor.tennisscoreboard.model.PlayerScore;
import com.github.petrovyegor.tennisscoreboard.model.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchScoreCalculationService {
    com.github.petrovyegor.tennisscoreboard.service.MatchScoreCalculationService matchScoreCalculationService = new com.github.petrovyegor.tennisscoreboard.service.MatchScoreCalculationService();

    //При старте игры у игроков счёт 0-0
    @Test
    public void testNewGameScoreLoveVSLove() {
        PlayerScore firstPlayerScore = new PlayerScore(0);
        PlayerScore secondPlayerScore = new PlayerScore(1);
        assertEquals(Point.LOVE, firstPlayerScore.getPoint());
        assertEquals(Point.LOVE, secondPlayerScore.getPoint());
    }

    //Если игрок 1 выигрывает очко при счёте 0-0, счёт становится 15-0
    @Test
    public void testFirstWonPointByPlayer_FifteenVSLove() {
        PlayerScore firstPlayerScore = new PlayerScore(0);
        PlayerScore secondPlayerScore = new PlayerScore(1);
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//15-0
        assertEquals(Point.FIFTEEN, firstPlayerScore.getPoint());
        assertEquals(Point.LOVE, secondPlayerScore.getPoint());
    }

    //Если игрок 1 выигрывает очко при счёте 15-0, счёт становится 30-0
    @Test
    public void testSecondWonPointByPlayer_ThirtyVSLove() {
        PlayerScore firstPlayerScore = new PlayerScore(0);
        PlayerScore secondPlayerScore = new PlayerScore(1);
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//15-0
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//30-0
        assertEquals(Point.THIRTY, firstPlayerScore.getPoint());
        assertEquals(Point.LOVE, secondPlayerScore.getPoint());
    }

    //Если игрок 1 выигрывает очко при счёте 30-0, счёт становится 40-0
    @Test
    public void testThirdWonPointByPlayer_FortyVSLove() {
        PlayerScore firstPlayerScore = new PlayerScore(0);
        PlayerScore secondPlayerScore = new PlayerScore(1);
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//15-0
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//30-0
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//40-0
        assertEquals(Point.FORTY, firstPlayerScore.getPoint());
        assertEquals(Point.LOVE, secondPlayerScore.getPoint());
    }

    //Если игрок 1 выигрывает очко при счёте 40-40, счёт становится AD-40
    @Test
    public void testSettingAdvantageWhenScore_FortyVSForty() {
        PlayerScore firstPlayerScore = new PlayerScore(0);
        PlayerScore secondPlayerScore = new PlayerScore(1);
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//15-0
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//30-0
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//40-0

        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);//40-15
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);//40-30
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);//40-40

        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//AD-40
        assertEquals(Point.ADVANTAGE, firstPlayerScore.getPoint());
        assertEquals(Point.FORTY, secondPlayerScore.getPoint());
    }

    //Если игрок 1 выигрывает очко при счёте 40-AD, счёт становится 40-40
    @Test
    public void testLosingAdvantageWhenScore_FortyVSAdvantage() {
        PlayerScore firstPlayerScore = new PlayerScore(0);
        PlayerScore secondPlayerScore = new PlayerScore(1);
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//15-0
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//30-0
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//40-0

        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);//40-15
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);//40-30
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);//40-40
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);//40-AD

        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//40-40
        assertEquals(Point.FORTY, firstPlayerScore.getPoint());
        assertEquals(Point.FORTY, secondPlayerScore.getPoint());
    }

    //Если игрок 1 выигрывает очко при счёте 40-40, гейм не заканчивается
    @Test
    public void testGameNotOverWhenFortyVSForty() {
        PlayerScore firstPlayerScore = new PlayerScore(0);
        PlayerScore secondPlayerScore = new PlayerScore(1);
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//15-0
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//30-0
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//40-0

        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);//40-15
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);//40-30
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);//40-40

        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//AD-40

        assertEquals(0, firstPlayerScore.getGames());
        assertEquals(0, secondPlayerScore.getGames());
    }

    //Если игрок 1 выигрывает очко при счёте AD-40, выигравший получает + 1 гейм
    @Test
    public void testGameOverWhenAdvantageVSForty() {
        PlayerScore firstPlayerScore = new PlayerScore(0);
        PlayerScore secondPlayerScore = new PlayerScore(1);
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//15-0
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//30-0
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//40-0

        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);//40-15
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);//40-30
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);//40-40

        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//AD-40
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//+1 гейм для Игрока1

        assertEquals(1, firstPlayerScore.getGames());
        assertEquals(0, secondPlayerScore.getGames());
    }

    // Если игрок 1 выигрывает очко при счёте 40-0, то он выигрывает и гейм
    @Test
    public void testGameOverWhenFortyVSLove() {
        PlayerScore firstPlayerScore = new PlayerScore(0);
        PlayerScore secondPlayerScore = new PlayerScore(1);
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//15-0
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//30-0
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//40-0
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//+1 гейм для Игрока1

        assertEquals(1, firstPlayerScore.getGames());
        assertEquals(0, secondPlayerScore.getGames());
    }

    //При счёте 6-6 начинается тайбрейк вместо обычного гейма+
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
        //выигрываем очко при счёте по геймам 6 - 6 -> добавляется очко в поле tieBreakPoint, счёт по очкам в тайбрейке становится 1-0
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        assertEquals(1, firstPlayerScore.getTieBreakPoints());
    }

    //Если игрок 1 выигрывает очко при счёте по геймам 6-0, выигравший получает +1 сэт
    @Test
    public void testSetOverWhen_6gamesVS0Games() {
        PlayerScore firstPlayerScore = new PlayerScore(0);
        PlayerScore secondPlayerScore = new PlayerScore(1);

        //выигрываем 6 геймов игроку1
        for (int i = 0; i < 24; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        }

        //По геймам счёт 6-0, должен приваться +1 сэт игроку1
        assertEquals(1, firstPlayerScore.getSets());
    }

    //Если игрок 1 выигрывает очко при счёте по геймам 6-4, игрок1 получает +1 сэт
    @Test
    public void testSetOverWhen_6gamesVS4Games() {
        PlayerScore firstPlayerScore = new PlayerScore(0);
        PlayerScore secondPlayerScore = new PlayerScore(1);

        //выигрываем 5 геймов игроку1
        for (int i = 0; i < 20; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        }

        //выигрываем 4 гейма игроку2
        for (int i = 0; i < 16; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);
        }

        ////выигрываем ещё 1 гейм игроку1, счёт по геймам становится 6-4, игрок1 получает +1 сэт
        for (int i = 0; i < 4; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        }

        assertEquals(1, firstPlayerScore.getSets());
    }

    //Если игрок 1 выигрывает гейм при счёте по геймам 5-5, он не выигрывает +1 сэт
    @Test
    public void testSetNotOverWhen_6gamesVS5Games() {
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

        ////выигрываем ещё 1 гейм игроку1, счёт по геймам становится 6-5, игрок1 не получает +1 сэт
        for (int i = 0; i < 4; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        }

        assertEquals(0, firstPlayerScore.getSets());
    }

    //В тайбрейке при счёте по очкам 7-0, игрок 1 выигрывает сэт
    @Test
    public void testTieBreakOverWhenTieBreakPointsScore_7VS0() {
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
        //выигрываем ещё по одному гейму каждому игроку, чтобы счё по геймам стал 6 - 6 и начался тайбрейк
        for (int i = 0; i < 4; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        }
        for (int i = 0; i < 4; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);
        }
        //выигрываем 7 очков в тайбрейке игроку1, счёт 7-0, он получает +1 сэт
        for (int i = 0; i < 7; i++){
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        }
        assertEquals(1, firstPlayerScore.getSets());
    }

    //В тайбрейке при счёте по очкам 7-5, игрок 1 выигрывает сэт
    @Test
    public void testTieBreakOverWhenTieBreakPointsScore_7VS5() {
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
        //выигрываем ещё по одному гейму каждому игроку, чтобы счё по геймам стал 6 - 6 и начался тайбрейк
        for (int i = 0; i < 4; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        }
        for (int i = 0; i < 4; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);
        }
        //выигрываем 6 очков в тайбрейке игроку1, счёт 6-0
        for (int i = 0; i < 6; i++){
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        }
        //выигрываем 5 очков в тайбрейке игроку2, счёт 6-5
        for (int i = 0; i < 5; i++){
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);
        }
        //выигрываем 1 очко в тайбреке игроку1, счёт 7-5, игрок 1 выигрывает +1 сэт
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        assertEquals(1, firstPlayerScore.getSets());
    }

    //В тайбрейке при счёте по очкам 7-6, игрок 1 не выигрывает сэт
    @Test
    public void testTieBreakOverWhenTieBreakPointsScore_7VS6() {
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
        //выигрываем ещё по одному гейму каждому игроку, чтобы счё по геймам стал 6 - 6 и начался тайбрейк
        for (int i = 0; i < 4; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        }
        for (int i = 0; i < 4; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);
        }
        //выигрываем 6 очков в тайбрейке игроку1, счёт 6-0
        for (int i = 0; i < 6; i++){
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        }
        //выигрываем 6 очков в тайбрейке игроку2, счёт 6-6
        for (int i = 0; i < 6; i++){
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);
        }
        //выигрываем 1 очко в тайбреке игроку1, счёт 7-6, игрок 1 не получает +1 сэт
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        assertEquals(0, firstPlayerScore.getSets());
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
}
