package com.github.petrovyegor.tennisscoreboard.service;

import com.github.petrovyegor.tennisscoreboard.dto.match_score.MatchScoreRequestDto;
import com.github.petrovyegor.tennisscoreboard.dto.ongoing_match.OngoingMatchDto;
import com.github.petrovyegor.tennisscoreboard.model.OngoingMatch;
import com.github.petrovyegor.tennisscoreboard.model.PlayerScore;

import java.util.UUID;

public class MatchScoreCalculationService {
    private final OngoingMatchesService ongoingMatchesService;

    public MatchScoreCalculationService() {
        ongoingMatchesService = new OngoingMatchesService();
    }

    public OngoingMatchDto processAction(MatchScoreRequestDto matchScoreRequestDto) {
        UUID matchUuid = matchScoreRequestDto.getMatchUuid();
        int roundWinnerId = matchScoreRequestDto.getRoundWinnerId();

        OngoingMatch ongoingMatch = ongoingMatchesService.findByUuid(matchUuid);

        PlayerScore roundWinnerScore = ongoingMatch.getWinnerScore(roundWinnerId);
        PlayerScore opponentScore = ongoingMatch.getOpponentScore(roundWinnerId);

        handleWonPoint(roundWinnerScore, opponentScore);

        OngoingMatchDto matchState = ongoingMatchesService.getMatchState(matchUuid);
        matchState.setMatchFinished(isWinnerExist(roundWinnerScore, opponentScore));
        matchState.setWinnerName(roundWinnerScore.getPlayerName());

        return matchState;
    }

    public void handleWonPoint(PlayerScore roundWinnerScore, PlayerScore opponentScore) {
        if (isTieBreak(roundWinnerScore, opponentScore)) {
            handleTieBreakPoint(roundWinnerScore, opponentScore);
        } else {
            handleRegularPoint(roundWinnerScore, opponentScore);
        }
        handleSetVictory(roundWinnerScore, opponentScore);
    }

    private void handleTieBreakPoint(PlayerScore roundWinnerScore, PlayerScore opponentScore) {
        roundWinnerScore.addTieBreakPoint();
        if (roundWinnerScore.getTieBreakPoints() >= 7
                && roundWinnerScore.getTieBreakPoints() - opponentScore.getTieBreakPoints() >= 2) {
            roundWinnerScore.winSet();
            roundWinnerScore.resetAfterSet();
            opponentScore.resetAfterSet();
        }
    }

    private void handleSetVictory(PlayerScore roundWinnerScore, PlayerScore opponentScore) {
        if (roundWinnerScore.getGames() == 6 && (roundWinnerScore.getGames() - opponentScore.getGames() >= 2)) {
            roundWinnerScore.winSet();
            roundWinnerScore.resetAfterSet();
            opponentScore.resetAfterSet();
        }
    }

    private void handleRegularPoint(PlayerScore roundWinnerScore, PlayerScore opponentScore) {
        if (isDeuce(roundWinnerScore, opponentScore)) {
            handleDeuce(roundWinnerScore, opponentScore);
        } else {
            if (isEarlyGameVictory(roundWinnerScore, opponentScore)) {
                handleEarlyGameVictory(roundWinnerScore, opponentScore);
            } else {
                roundWinnerScore.addPoint();
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

    private boolean isWinnerExist(PlayerScore firstPlayerScore, PlayerScore secondPlayerScore) {
        return firstPlayerScore.getSets() == 2 || secondPlayerScore.getSets() == 2;
    }
}