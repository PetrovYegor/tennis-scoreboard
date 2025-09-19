package com.github.petrovyegor.tennisscoreboard.service;

import com.github.petrovyegor.tennisscoreboard.MatchManager;
import com.github.petrovyegor.tennisscoreboard.dao.MemoryOngoingMatchDao;
import com.github.petrovyegor.tennisscoreboard.dto.MatchScoreRequestDto;
import com.github.petrovyegor.tennisscoreboard.model.MatchScore;
import com.github.petrovyegor.tennisscoreboard.model.OngoingMatch;
import com.github.petrovyegor.tennisscoreboard.model.PlayerScore;

import java.util.UUID;

public class MatchScoreCalculationService {
    private final MemoryOngoingMatchDao memOngoingMatchDao = new MemoryOngoingMatchDao();
    private boolean isMatchOver = false;


    public void processAction(MatchScoreRequestDto matchScoreRequestDto) {
        if (!isMatchOver){
            UUID matchUuid = matchScoreRequestDto.getMatchUuid();
            int roundWinnerId = matchScoreRequestDto.getRoundWinnerId();
            int firstPlayerId = matchScoreRequestDto.getFirstPlayerId();
            int secondPlayerId = matchScoreRequestDto.getSecondPlayerId();
            OngoingMatch ongoingMatch = memOngoingMatchDao.findById(matchUuid).get();//переписать
            MatchScore matchScore = ongoingMatch.getMatchScore();
            PlayerScore firstPlayerScore = matchScore.getPlayersScore().get(firstPlayerId);
            PlayerScore secondPlayerScore = matchScore.getPlayersScore().get(secondPlayerId);
            MatchManager matchManager = new MatchManager(firstPlayerScore, secondPlayerScore, 6);
            isMatchOver = matchManager.processWonPoint(roundWinnerId);

            //throw new IllegalStateException("Сюда не должно доходить выполнение кода");//временно, переделать
        }
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("МАТЧ ОКОНЧЕН БОЛЬШЕ ОЧКОВ НЕ БУДЕТ ПРИБАВЛЯТЬСЯ");
    }
}



