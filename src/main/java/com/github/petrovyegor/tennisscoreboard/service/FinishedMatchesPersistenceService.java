package com.github.petrovyegor.tennisscoreboard.service;

import com.github.petrovyegor.tennisscoreboard.dao.JpaMatchDao;
import com.github.petrovyegor.tennisscoreboard.dao.MemoryOngoingMatchDao;
import com.github.petrovyegor.tennisscoreboard.dto.MatchScoreResponseDto;
import com.github.petrovyegor.tennisscoreboard.model.entity.Match;

import java.util.UUID;

public class FinishedMatchesPersistenceService {
    private final JpaMatchDao jpaMatchDao = new JpaMatchDao();
    private final OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();
    private final MemoryOngoingMatchDao memoryOngoingMatchDao = new MemoryOngoingMatchDao();
    //Если матч закончился - через `FinishedMatchesPersistenceService` сохраняет
    // законченный матч в базу данных. инкапсулирует чтение и запись законченных
    // матчей в БД

    public void processFinishedMatch(MatchScoreResponseDto matchScoreResponseDto){
        saveMatch(matchScoreResponseDto);
        UUID matchId = matchScoreResponseDto.getOngoingMatchDto().getMatchUuid();
        memoryOngoingMatchDao.delete(matchId);
    }

    public void saveMatch(MatchScoreResponseDto matchScoreResponseDto) {
        Match finishedMatch = toMatch(matchScoreResponseDto);
        jpaMatchDao.save(finishedMatch);
    }

    private Match toMatch(MatchScoreResponseDto matchScoreResponseDto) {
        int firstPlayerId = matchScoreResponseDto.getOngoingMatchDto().getFirstPlayerScore().getPlayerId();
        int secondPlayerId = matchScoreResponseDto.getOngoingMatchDto().getSecondPlayerScore().getPlayerId();
        int winnerId = matchScoreResponseDto.getWinnerId();
        return new Match(
                firstPlayerId,
                secondPlayerId,
                winnerId
        );
    }
}
