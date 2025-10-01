package com.github.petrovyegor.tennisscoreboard.service;

import com.github.petrovyegor.tennisscoreboard.dao.JpaMatchDao;
import com.github.petrovyegor.tennisscoreboard.dao.MemoryOngoingMatchDao;
import com.github.petrovyegor.tennisscoreboard.dto.RoundResultDto;
import com.github.petrovyegor.tennisscoreboard.model.entity.Match;

import java.util.UUID;

public class FinishedMatchesPersistenceService {
    private final JpaMatchDao jpaMatchDao = new JpaMatchDao();
    private final OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();
    private final MemoryOngoingMatchDao memoryOngoingMatchDao = new MemoryOngoingMatchDao();
    //Если матч закончился - через `FinishedMatchesPersistenceService` сохраняет
    // законченный матч в базу данных. инкапсулирует чтение и запись законченных
    // матчей в БД

    public void processFinishedMatch(RoundResultDto roundResultDto){
        saveMatch(roundResultDto);
        UUID matchId = roundResultDto.getMatchUuid();
        memoryOngoingMatchDao.delete(matchId);
    }

    public void saveMatch(RoundResultDto roundResultDto) {
        Match finishedMatch = toMatch(roundResultDto);
        jpaMatchDao.save(finishedMatch);
    }

    private Match toMatch(RoundResultDto roundResultDto) {
        int firstPlayerId = roundResultDto.getFirstPlayerId();
        int secondPlayerId = roundResultDto.getSecondPlayerId();
        int winnerId = roundResultDto.getWinnerId();
        return new Match(
                firstPlayerId,
                secondPlayerId,
                winnerId
        );
    }
}
