package com.github.petrovyegor.tennisscoreboard;

import com.github.petrovyegor.tennisscoreboard.model.PlayerScore;

public class MatchManager {
    private PlayerScore firstPlayerScore;
    private PlayerScore secondPlayerScore;
    private final int GAMES_TO_TIE_BREAK = 6;

    public MatchManager(PlayerScore firstPlayerScore, PlayerScore secondPlayerScore) {
        this.firstPlayerScore = firstPlayerScore;
        this.secondPlayerScore = secondPlayerScore;
    }

    //public void processWonPoint(int pointWinnerId) {
    public boolean processWonPoint(int pointWinnerId) {//булеан временно
        PlayerScore winnerScore = getWinnerScore(pointWinnerId);
        PlayerScore opponentScore = getOpponentScore(pointWinnerId);
        if (isTieBreak()) {
            handleTieBreakPoint(winnerScore, opponentScore);
        } else {
            handleRegularPoint(winnerScore, opponentScore);
            handleSetVictory(winnerScore, opponentScore);
        }

        if (isWinnerExists()) {
            return true;
            //сохранение матча
            //редирект на странрицу с результатами матча
        } else {
            //редирект опять на страницу с матчем
            return false;
        }
    }

    private void handleTieBreakPoint(PlayerScore winnerScore, PlayerScore opponentScore) {
        winnerScore.addTieBreakPoint();
        if (winnerScore.getTieBreakPoints() >= 7
                && winnerScore.getTieBreakPoints() - opponentScore.getTieBreakPoints() >= 2) {
            winnerScore.winSet();
            winnerScore.resetTieBreakPoint();
            opponentScore.resetAfterSet();//этот блок кода не нравится, переделать
            opponentScore.resetTieBreakPoint();
        }
    }

    private void handleSetVictory(PlayerScore winnerScore, PlayerScore opponentScore){
        //условие выигрыша сэта - у победиля имеется 6 геймов, а у соперника 4 или меньше
        //
            if (winnerScore.getGames() == 6 && (winnerScore.getGames() - opponentScore.getGames() >= 2)){
                winnerScore.winSet();
                opponentScore.resetAfterSet();//тут остановился, уже сплю. тут нужно проставлять матч из овер и убедиться, что сеты во всех случаях тут обрабатываются
        }
    }

    private void handleRegularPoint(PlayerScore winnerScore, PlayerScore opponentScore) {
        if (isDeuce()) {
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
        opponentScore.resetAfterGame();
    }

    private void handleDeuce(PlayerScore winnerScore, PlayerScore opponentScore) {
        if (winnerScore.isAdvantage()) {
            winnerScore.winGame();
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

    private boolean isDeuce() {
        return firstPlayerScore.isFortyOrAd() && secondPlayerScore.isFortyOrAd();
    }

    private boolean isTieBreak() {
        return firstPlayerScore.getGames() == GAMES_TO_TIE_BREAK && secondPlayerScore.getGames() == GAMES_TO_TIE_BREAK;
    }

    private PlayerScore getWinnerScore(int pointWinnerId) {
        if (pointWinnerId == firstPlayerScore.getPlayerId()) {
            return firstPlayerScore;
        }
        return secondPlayerScore;
    }

    private PlayerScore getOpponentScore(int pointWinnerId) {
        if (pointWinnerId == firstPlayerScore.getPlayerId()) {
            return secondPlayerScore;
        }
        return firstPlayerScore;
    }

    private boolean isWinnerExists(){
        return firstPlayerScore.getSets() == 2 || secondPlayerScore.getSets() == 2;
    }
}
