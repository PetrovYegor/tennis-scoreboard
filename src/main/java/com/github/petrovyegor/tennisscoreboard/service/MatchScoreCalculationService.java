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

        //сделать развилку иф - если виннерИд равен первому игроку - то processRound с одной последовательностью ид, иначе с другой
        //ВОЗМОЖНО НУЖНО ВОЗВРАЩАТЬ КАКОЕ-то ДТО!!!!!!
    }

    private boolean isEqualsForty(Point point){
        return point.equals(Point.FORTY);
    }

    private void processRound(PlayerScore winnerScore, PlayerScore enemyScore, int winnerId){
        Point winnerCurrentPoint = winnerScore.getCurrentPoint();
        Point enemyCurrentPoint = enemyScore.getCurrentPoint();//переименовать enemy
        if (!isEqualsForty(winnerCurrentPoint)){
            winnerScore.addPoint();
            return;
        }

        if (!isEqualsForty(enemyCurrentPoint)){
            winnerScore.addGame();
            winnerScore.resetPoint();//тут возможно придётся булеаны сбрасывать, их пока нет
            enemyScore.resetPoint();
            return;
        }

        if (!winnerScore.isHasAdvantage() && !enemyScore.isHasAdvantage()){
            winnerScore.setAdvantage();
            return;
        }

        if (winnerScore.getPlayerId() != winnerId){
            winnerScore.resetAdvantage();
        }



    }


}


