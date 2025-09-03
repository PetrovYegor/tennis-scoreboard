package com.github.petrovyegor.tennisscoreboard.service;

import com.github.petrovyegor.tennisscoreboard.dao.JpaPlayerDao;
import com.github.petrovyegor.tennisscoreboard.dao.MemoryOngoingMatchDao;
import com.github.petrovyegor.tennisscoreboard.dto.MatchScoreRequestDto;
import com.github.petrovyegor.tennisscoreboard.model.MatchScore;
import com.github.petrovyegor.tennisscoreboard.model.OngoingMatch;
import com.github.petrovyegor.tennisscoreboard.model.PlayerScore;

import java.util.UUID;

public class MatchScoreCalculationService {
    private final JpaPlayerDao jpaPlayerDao = new JpaPlayerDao();
    private final MemoryOngoingMatchDao memOngoingMatchDao = new MemoryOngoingMatchDao();
    private boolean isTieBreak;
    private boolean isMatchOver;

    public void processAction(MatchScoreRequestDto matchScoreRequestDto) {

        UUID matchUuid = matchScoreRequestDto.getMatchUuid();
        int roundWinnerId = matchScoreRequestDto.getRoundWinnerId();
        int firstPlayerId = matchScoreRequestDto.getFirstPlayerId();
        int secondPlayerId = matchScoreRequestDto.getSecondPlayerId();
        OngoingMatch ongoingMatch = memOngoingMatchDao.findById(matchUuid).get();//переписать
        MatchScore matchScore = ongoingMatch.getMatchScore();
        PlayerScore firstPlayerScore = matchScore.getPlayersScore().get(firstPlayerId);
        PlayerScore secondPlayerScore = matchScore.getPlayersScore().get(secondPlayerId);
        //нужна проверка, что если после очка завершился матч, то через отдельный сервис проставляем нужным сущностям ид игрока, выигравшего матч и сохраняем его в БД
        if (roundWinnerId == firstPlayerId) {
            processPoint(firstPlayerScore, secondPlayerScore);
            return;
        } else if (roundWinnerId == secondPlayerId) {
            processPoint(secondPlayerScore, firstPlayerScore);
            return;
        }
        throw new IllegalStateException("Сюда не должно доходить выполнение кода");//временно, переделать
    }

    private void processPoint(PlayerScore winnerScore, PlayerScore opponentScore) {
        if (isTieBreak) {
            processTieBreak(winnerScore, opponentScore);
            //должны очиститься очки, геймы, закончиться матч и сохраниться. Редиректнуться на новую страницу
        }
        processGame(winnerScore, opponentScore);
        if (isGameOver(winnerScore)) {//---Поинт победителя равен Game
            processFinishedGame(winnerScore, opponentScore);
        }
        checkIfMatchIsOver(winnerScore, opponentScore);
        if (isMatchOver) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("Match is over");
            System.out.println("redirecting to another jsp");
            //!!!!!!!!!!!!!!!!!!!!!!!!!!! сохранить матч в БД
            //сохранить матч в БД
            //редиректнуть на страницу со счётом матча
        }


    }

    private void processFinishedGame(PlayerScore winnerScore, PlayerScore opponentScore) {
        winnerScore.addGame();
        resetPointsAndAdvantage(winnerScore, opponentScore);
    }

    private void processGame(PlayerScore winnerScore, PlayerScore opponentScore) {
        if (isDeuce(winnerScore, opponentScore)) {//если равно - отыгрываем равно
            processDeuce(winnerScore, opponentScore);
        } else {
            processRound(winnerScore, opponentScore);
        }
    }

    private boolean isDeuce(PlayerScore firstPlayerScore, PlayerScore secondPlayerScore) {//40 или AD
        boolean firstPlayerHasFortyOrAD = firstPlayerScore.isFortyOrAd();
        boolean secondPlayerHasFortyOrAd = secondPlayerScore.isFortyOrAd();
        return firstPlayerHasFortyOrAD && secondPlayerHasFortyOrAd;
    }

    private void checkIfMatchIsOver(PlayerScore winnerScore, PlayerScore opponentScore) {
/*
после processRound у выигравшего очко стало 6 геймов?
	да
		имеется разница в 2 и более гейма относительно соперника?
			да
				выигравший игрок уже имеет 1 сэт за плечами?
					да
						выигравший очко игрок выигрывает матч, геймы обнуляются, поинты и константы тоже, матч сохраняется в БД
					нет
						выигравший очко получает +сэт, геймы обнуляются, поинты и булеаны тоже
			нет
				количество геймов соперника равно 6?
					да
						запускается тайбрейк
					нет
						играется следующий гейм

	нет
		играется следующий гейм
* */
        if (winnerScore.getGames() == 6) {//после processRound у выигравшего очко стало 6 геймов?
            if (winnerScore.getGames() - opponentScore.getGames() >= 2) {//имеется разница в 2 и более гейма относительно соперника?
                if (winnerScore.getSets() == 1) {//выигравший игрок уже имеет 1 сэт за плечами?
                    isMatchOver = true;
                }
                winnerScore.addSet();
                winnerScore.resetGames();
                resetGame(winnerScore, opponentScore);
            }
        } else {
            if (opponentScore.getGames() == 6) {
                isTieBreak = true;
            }

        }
    }

    private boolean isGameOver(PlayerScore winnerScore) {
        return winnerScore.isEqualsGame();
    }

    private void processDeuce(PlayerScore winnerScore, PlayerScore opponentScore) {
/*
					если преимущество у получившего очко
							выигрывший получает Поинт Game и return

					если преимущество у соперника
							соперник теряет преимущество Enum Advantage до 40 и return

					добавляем преимущество выигрывшему игроку и return
* */
        if (winnerScore.hasAdvantage()) {
            winnerScore.addPoint();
            return;
        }

        if (opponentScore.hasAdvantage()) {
            opponentScore.loseAdvantage();//переименовать в lose?
            return;
        }
        winnerScore.setAdvantage();
    }

    private void processTieBreak(PlayerScore winnerScore, PlayerScore opponentScore) {
        winnerScore.addTieBreakPoint();
        int firstPlayerTieBreakScore = winnerScore.getTieBreakScore();
        int secondPlayerTieBreakScore = opponentScore.getTieBreakScore();
        if (firstPlayerTieBreakScore >= 7 && (firstPlayerTieBreakScore - secondPlayerTieBreakScore >= 2)) {//какой-то игрок уже имеет 7 очков?
            winnerScore.addSet();
            resetGame(winnerScore, opponentScore);

        }
/*
	да
		какой-то игрок уже имеет 7 очков?
			да
				этот игрок имеет отрыв в 2 очка по сравнению с соперником?
					да
						тай брейк окончен
						прибавляется сэт победителю
						очищаются поинты и константы
					нет
						играем дальше
			нет
				играем дальше
	нет
		выигрывший очко сейчас
Если после тайбрейка количество сетов = 2 - матч заканчивается, обнуляются поинты, геймы, булеаны, матч сохраняется в БД
* */

    }


    private void processRound(PlayerScore winnerScore, PlayerScore opponentScore) {
        if (isEarlyVictory(winnerScore, opponentScore)) {
            winnerScore.assignGame();
        } else {
            winnerScore.addPoint();
        }
    }

    private boolean isEarlyVictory(PlayerScore winnerScore, PlayerScore opponentScore) {//----текущий счёт выигрывшего очко равен 40, а счёт соперника не равен 40 или AD Game?
        boolean winnerHasForty = winnerScore.isEqualsForty();
        boolean opponentHasFortyOrAdvantage = opponentScore.isFortyOrAd();
        return winnerHasForty && !opponentHasFortyOrAdvantage;
    }


    private void resetPointsAndAdvantage(PlayerScore firstPlayerScore, PlayerScore secondPlayerScore) {
        firstPlayerScore.resetPoint();
        secondPlayerScore.resetPoint();
        firstPlayerScore.loseAdvantage();
        secondPlayerScore.loseAdvantage();
    }

    private void resetGame(PlayerScore winnerScore, PlayerScore opponentScore) {//переименовать
        winnerScore.resetGames();
        opponentScore.resetGames();
        resetPointsAndAdvantage(winnerScore, opponentScore);
    }
}


