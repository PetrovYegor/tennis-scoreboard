package com.github.petrovyegor.tennisscoreboard;

import com.github.petrovyegor.tennisscoreboard.model.PlayerScore;
import com.github.petrovyegor.tennisscoreboard.model.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MatchScoreCalculationServiceTest {
    com.github.petrovyegor.tennisscoreboard.service.MatchScoreCalculationService matchScoreCalculationService = new com.github.petrovyegor.tennisscoreboard.service.MatchScoreCalculationService();

    // When the match starts, players have 0-0 score
    @Test
    public void testNewGameScoreLoveVSLove() {
        PlayerScore firstPlayerScore = new PlayerScore(0);
        PlayerScore secondPlayerScore = new PlayerScore(1);
        assertEquals(Point.LOVE, firstPlayerScore.getPoint());
        assertEquals(Point.LOVE, secondPlayerScore.getPoint());
    }

    // When the match starts, there is no winners
//    @Test
//    public void testNewMatchHasNotWinners() {
//        PlayerScore firstPlayerScore = new PlayerScore(0);
//        PlayerScore secondPlayerScore = new PlayerScore(1);
//        assertFalse(matchScoreCalculationService.isWinnerExists(firstPlayerScore, secondPlayerScore));
//    }

    // if player1 got 2 sets, player1 becomes a winner
//    @Test
//    public void testFinishedMatchHasWinner() {
//        PlayerScore firstPlayerScore = new PlayerScore(0);
//        PlayerScore secondPlayerScore = new PlayerScore(1);
//        // win 2 sets for player1
//        for (int i = 0; i < 48; i++) {
//            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
//        }
//        assertTrue(matchScoreCalculationService.isWinnerExists(firstPlayerScore, secondPlayerScore));
//    }

    // If player 1 wins a point at 0-0, score becomes 15-0
    @Test
    public void testFirstWonPointByPlayer_FifteenVSLove() {
        PlayerScore firstPlayerScore = new PlayerScore(0);
        PlayerScore secondPlayerScore = new PlayerScore(1);
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//15-0
        assertEquals(Point.FIFTEEN, firstPlayerScore.getPoint());
        assertEquals(Point.LOVE, secondPlayerScore.getPoint());
    }

    // If player 1 wins a point at 15-0, score becomes 30-0
    @Test
    public void testSecondWonPointByPlayer_ThirtyVSLove() {
        PlayerScore firstPlayerScore = new PlayerScore(0);
        PlayerScore secondPlayerScore = new PlayerScore(1);
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//15-0
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//30-0
        assertEquals(Point.THIRTY, firstPlayerScore.getPoint());
        assertEquals(Point.LOVE, secondPlayerScore.getPoint());
    }

    // If player 1 wins a point at 30-0, score becomes 40-0
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

    // If player 1 wins a point at 40-40, score becomes AD-40
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

    // If player 1 wins a point at 40-AD, score becomes 40-40
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

    // If player 1 wins a point at 40-40, the game doesn't end
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

    // If player 1 wins a point at AD-40, the winner gets +1 game
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
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//+1 game for Player1

        assertEquals(1, firstPlayerScore.getGames());
        assertEquals(0, secondPlayerScore.getGames());
    }

    // If player 1 wins a point at 40-0, they win the game
    @Test
    public void testGameOverWhenFortyVSLove() {
        PlayerScore firstPlayerScore = new PlayerScore(0);
        PlayerScore secondPlayerScore = new PlayerScore(1);
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//15-0
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//30-0
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//40-0
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);//+1 game for Player1

        assertEquals(1, firstPlayerScore.getGames());
        assertEquals(0, secondPlayerScore.getGames());
    }

    // When score is 6-6, tie-break starts instead of regular game
    @Test
    public void testTieBreakStartWhen6GamesVS6Games() {
        PlayerScore firstPlayerScore = new PlayerScore(0);
        PlayerScore secondPlayerScore = new PlayerScore(1);

        // win 5 games for player1
        for (int i = 0; i < 20; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        }

        // win 5 games for player2
        for (int i = 0; i < 20; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);
        }
        // win one more game for each player to make game score 6-6
        for (int i = 0; i < 4; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        }
        for (int i = 0; i < 4; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);
        }
        // win a point at 6-6 game score -> tieBreakPoint field gets incremented, tie-break score becomes 1-0
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        assertEquals(1, firstPlayerScore.getTieBreakPoints());
    }

    // If player 1 wins a point at 6-0 game score, the winner gets +1 set
    @Test
    public void testSetOverWhen_6gamesVS0Games() {
        PlayerScore firstPlayerScore = new PlayerScore(0);
        PlayerScore secondPlayerScore = new PlayerScore(1);

        // win 6 games for player1
        for (int i = 0; i < 24; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        }

        // Game score is 6-0, player1 should get +1 set
        assertEquals(1, firstPlayerScore.getSets());
    }

    // If player 1 wins a point at 6-4 game score, player1 gets +1 set
    @Test
    public void testSetOverWhen_6gamesVS4Games() {
        PlayerScore firstPlayerScore = new PlayerScore(0);
        PlayerScore secondPlayerScore = new PlayerScore(1);

        // win 5 games for player1
        for (int i = 0; i < 20; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        }

        // win 4 games for player2
        for (int i = 0; i < 16; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);
        }

        // win one more game for player1, game score becomes 6-4, player1 gets +1 set
        for (int i = 0; i < 4; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        }

        assertEquals(1, firstPlayerScore.getSets());
    }

    // If player 1 wins a game at 5-5 game score, they don't win +1 set
    @Test
    public void testSetNotOverWhen_6gamesVS5Games() {
        PlayerScore firstPlayerScore = new PlayerScore(0);
        PlayerScore secondPlayerScore = new PlayerScore(1);

        // win 5 games for player1
        for (int i = 0; i < 20; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        }

        // win 5 games for player2
        for (int i = 0; i < 20; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);
        }

        // win one more game for player1, game score becomes 6-5, player1 doesn't get +1 set
        for (int i = 0; i < 4; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        }

        assertEquals(0, firstPlayerScore.getSets());
    }

    // In tie-break at 7-0 points score, player 1 wins the set
    @Test
    public void testTieBreakOverWhenTieBreakPointsScore_7VS0() {
        PlayerScore firstPlayerScore = new PlayerScore(0);
        PlayerScore secondPlayerScore = new PlayerScore(1);

        // win 5 games for player1
        for (int i = 0; i < 20; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        }

        // win 5 games for player2
        for (int i = 0; i < 20; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);
        }
        // win one more game for each player to make game score 6-6 and start tie-break
        for (int i = 0; i < 4; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        }
        for (int i = 0; i < 4; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);
        }
        // win 7 tie-break points for player1, score 7-0, they get +1 set
        for (int i = 0; i < 7; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        }
        assertEquals(1, firstPlayerScore.getSets());
    }

    // In tie-break at 7-5 points score, player 1 wins the set
    @Test
    public void testTieBreakOverWhenTieBreakPointsScore_7VS5() {
        PlayerScore firstPlayerScore = new PlayerScore(0);
        PlayerScore secondPlayerScore = new PlayerScore(1);

        // win 5 games for player1
        for (int i = 0; i < 20; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        }

        // win 5 games for player2
        for (int i = 0; i < 20; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);
        }
        // win one more game for each player to make game score 6-6 and start tie-break
        for (int i = 0; i < 4; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        }
        for (int i = 0; i < 4; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);
        }
        // win 6 tie-break points for player1, score 6-0
        for (int i = 0; i < 6; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        }
        // win 5 tie-break points for player2, score 6-5
        for (int i = 0; i < 5; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);
        }
        // win 1 tie-break point for player1, score 7-5, player 1 wins +1 set
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        assertEquals(1, firstPlayerScore.getSets());
    }

    // In tie-break at 7-6 points score, player 1 doesn't win the set
    @Test
    public void testTieBreakOverWhenTieBreakPointsScore_7VS6() {
        PlayerScore firstPlayerScore = new PlayerScore(0);
        PlayerScore secondPlayerScore = new PlayerScore(1);

        // win 5 games for player1
        for (int i = 0; i < 20; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        }

        // win 5 games for player2
        for (int i = 0; i < 20; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);
        }
        // win one more game for each player to make game score 6-6 and start tie-break
        for (int i = 0; i < 4; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        }
        for (int i = 0; i < 4; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);
        }
        // win 6 tie-break points for player1, score 6-0
        for (int i = 0; i < 6; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        }
        // win 6 tie-break points for player2, score 6-6
        for (int i = 0; i < 6; i++) {
            matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 1);
        }
        // win 1 tie-break point for player1, score 7-6, player 1 doesn't get +1 set
        matchScoreCalculationService.handleWonPoint(firstPlayerScore, secondPlayerScore, 0);
        assertEquals(0, firstPlayerScore.getSets());
    }
}
