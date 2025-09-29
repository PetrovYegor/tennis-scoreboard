package com.github.petrovyegor.tennisscoreboard.service;

import com.github.petrovyegor.tennisscoreboard.dto.MatchScoreRequestDto;
import com.github.petrovyegor.tennisscoreboard.model.OngoingMatch;
import com.github.petrovyegor.tennisscoreboard.model.PlayerScore;

import java.util.UUID;

public class MatchScoreCalculationService {
    private final OngoingMatchesService ongoingMatchesService;

    public MatchScoreCalculationService() {
        this.ongoingMatchesService = new OngoingMatchesService();
    }

    public void processAction(MatchScoreRequestDto matchScoreRequestDto) {
        UUID matchUuid = matchScoreRequestDto.getMatchUuid();
        int roundWinnerId = matchScoreRequestDto.getRoundWinnerId();
        OngoingMatch ongoingMatch = ongoingMatchesService.findByUuid(matchUuid);
        PlayerScore firstPlayerScore = ongoingMatch.getPlayerScore(ongoingMatch.getFirstPlayer());
        PlayerScore secondPlayerScore = ongoingMatch.getPlayerScore(ongoingMatch.getSecondPlayer());
        handleWonPoint(firstPlayerScore, secondPlayerScore, roundWinnerId);
        if (isWinnerExists(firstPlayerScore, secondPlayerScore)) {
            //сохранение матча
            //редирект на странрицу с результатами матча
        } else {//мб не через else, мб выше будет ретурн
            //редирект опять на страницу с матчем
        }
    }

    public void handleWonPoint(PlayerScore firstPlayerScore, PlayerScore secondPlayerScore, int pointWinnerId) {//булеан временно
        PlayerScore winnerScore = getWinnerScore(pointWinnerId, firstPlayerScore, secondPlayerScore);
        PlayerScore opponentScore = getOpponentScore(pointWinnerId, firstPlayerScore, secondPlayerScore);
        if (isTieBreak(firstPlayerScore, secondPlayerScore)) {
            handleTieBreakPoint(winnerScore, opponentScore);
        } else {
            handleRegularPoint(winnerScore, opponentScore);
        }
        handleSetVictory(winnerScore, opponentScore);
    }

    private void handleTieBreakPoint(PlayerScore winnerScore, PlayerScore opponentScore) {
        winnerScore.addTieBreakPoint();
        if (winnerScore.getTieBreakPoints() >= 7
                && winnerScore.getTieBreakPoints() - opponentScore.getTieBreakPoints() >= 2) {
            winnerScore.winSet();
            winnerScore.resetAfterSet();
            opponentScore.resetAfterSet();
        }
    }

    private void handleSetVictory(PlayerScore winnerScore, PlayerScore opponentScore) {
        if (winnerScore.getGames() == 6 && (winnerScore.getGames() - opponentScore.getGames() >= 2)) {
            winnerScore.winSet();
            winnerScore.resetAfterSet();
            opponentScore.resetAfterSet();
        }
    }

    private void handleRegularPoint(PlayerScore winnerScore, PlayerScore opponentScore) {
        if (isDeuce(winnerScore, opponentScore)) {
            handleDeuce(winnerScore, opponentScore);
        } else {
            if (isEarlyGameVictory(winnerScore, opponentScore)) {
                handleEarlyGameVictory(winnerScore, opponentScore);
            } else {
                winnerScore.addPoint();
            }
        }
    }

    private void handleEarlyGameVictory(PlayerScore winnerScore, PlayerScore opponentScore) {
        winnerScore.winGame();
        winnerScore.resetAfterGame();
        opponentScore.resetAfterGame();
    }

    private void handleDeuce(PlayerScore winnerScore, PlayerScore opponentScore) {
        if (winnerScore.isAdvantage()) {
            winnerScore.winGame();
            winnerScore.resetAfterGame();
            opponentScore.resetAfterGame();
        } else if (opponentScore.isAdvantage()) {
            opponentScore.loseAdvantage();
        } else {
            winnerScore.setAdvantage();
        }
    }

    private boolean isEarlyGameVictory(PlayerScore winnerScore, PlayerScore opponentScore) {
        return winnerScore.isEqualsForty() && opponentScore.isUnderForty();
    }

    private boolean isDeuce(PlayerScore firstPlayerScore, PlayerScore secondPlayerScore) {
        return firstPlayerScore.isFortyOrAd() && secondPlayerScore.isFortyOrAd();
    }

    private boolean isTieBreak(PlayerScore firstPlayerScore, PlayerScore secondPlayerScore) {
        return firstPlayerScore.getGames() == 6 && secondPlayerScore.getGames() == 6;
    }

    private PlayerScore getWinnerScore(int pointWinnerId, PlayerScore firstPlayerScore, PlayerScore secondPlayerScore) {
        if (pointWinnerId == firstPlayerScore.getPlayerId()) {
            return firstPlayerScore;
        }
        return secondPlayerScore;
    }

    private PlayerScore getOpponentScore(int pointWinnerId, PlayerScore firstPlayerScore, PlayerScore secondPlayerScore) {
        if (pointWinnerId == firstPlayerScore.getPlayerId()) {
            return secondPlayerScore;
        }
        return firstPlayerScore;
    }

    public boolean isWinnerExists(PlayerScore firstPlayerScore, PlayerScore secondPlayerScore) {
        return firstPlayerScore.getSets() == 2 || secondPlayerScore.getSets() == 2;
    }
}