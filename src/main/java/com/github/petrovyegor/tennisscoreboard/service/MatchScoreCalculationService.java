package com.github.petrovyegor.tennisscoreboard.service;

import com.github.petrovyegor.tennisscoreboard.dao.JpaPlayerDao;
import com.github.petrovyegor.tennisscoreboard.dao.MemoryOngoingMatchDao;
import com.github.petrovyegor.tennisscoreboard.dto.MatchScoreRequestDto;
import com.github.petrovyegor.tennisscoreboard.model.MatchScore;
import com.github.petrovyegor.tennisscoreboard.model.OngoingMatch;
import com.github.petrovyegor.tennisscoreboard.model.PlayerScore;
import com.github.petrovyegor.tennisscoreboard.model.Point;

import java.util.UUID;

public class MatchScoreCalculationService {
    //обновляет счёт в матче. Реализует логику подсчёта счёта матча по очкам/геймам/сетам
    //имя, количество сетов, количество геймов, текущее начение Point
    private final JpaPlayerDao jpaPlayerDao = new JpaPlayerDao();
    private final MemoryOngoingMatchDao memOngoingMatchDao = new MemoryOngoingMatchDao();

    //есть ид игрока
    //получить его счёт и увеличить в счёте Point
    //мб редиректнуть

    //потом уже добавить проверку на гейм закончился или сэт
    public void addPoint(int playerId) {

    }

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
         if (roundWinnerId == firstPlayerId){
            processRound(firstPlayerScore, secondPlayerScore);
            return;
        } else if (roundWinnerId == secondPlayerId) {
            processRound(secondPlayerScore, firstPlayerScore);
            return;
        }
        throw new IllegalStateException("Сюда не должно доходить выполнение кода");//временно, переделать
    }

    private boolean isEqualsForty(Point point) {
        return point.equals(Point.FORTY);
    }

    private void processRound(PlayerScore winnerScore, PlayerScore enemyScore) {
        Point winnerCurrentPoint = winnerScore.getCurrentPoint();
        Point enemyCurrentPoint = enemyScore.getCurrentPoint();//переименовать enemy
        if (!isEqualsForty(winnerCurrentPoint)) {
            winnerScore.addPoint();
            return;
        }

        if (!isEqualsForty(enemyCurrentPoint)) {
            winnerScore.addGame();
            resetPoints(winnerScore, enemyScore);
            return;
        }

        if (!winnerScore.isHasAdvantage() && !enemyScore.isHasAdvantage()) {
            winnerScore.setAdvantage();
            return;
        }

        if (!winnerScore.isHasAdvantage() ){
            enemyScore.resetAdvantage();
            return;
        }

        winnerScore.addGame();
        resetPoints(winnerScore, enemyScore);
    }

    private void resetPoints(PlayerScore firstPlayerScore, PlayerScore secondPlayerScore) {
        firstPlayerScore.resetPoint();
        secondPlayerScore.resetPoint();
        firstPlayerScore.resetAdvantage();
        secondPlayerScore.resetAdvantage();
    }
}


