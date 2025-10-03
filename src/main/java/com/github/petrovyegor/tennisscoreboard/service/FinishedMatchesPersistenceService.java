package com.github.petrovyegor.tennisscoreboard.service;

import com.github.petrovyegor.tennisscoreboard.dao.JpaMatchDao;
import com.github.petrovyegor.tennisscoreboard.dao.MemoryOngoingMatchDao;
import com.github.petrovyegor.tennisscoreboard.dto.MatchScoreRequestDto;
import com.github.petrovyegor.tennisscoreboard.model.OngoingMatch;
import com.github.petrovyegor.tennisscoreboard.model.entity.Match;

import java.util.UUID;

public class FinishedMatchesPersistenceService {
    private final JpaMatchDao jpaMatchDao = new JpaMatchDao();
    private final OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();
    private final MemoryOngoingMatchDao memoryOngoingMatchDao = new MemoryOngoingMatchDao();
    //Если матч закончился - через `FinishedMatchesPersistenceService` сохраняет
    // законченный матч в базу данных. инкапсулирует чтение и запись законченных
    // матчей в БД

    public void processFinishedMatch(MatchScoreRequestDto matchScoreRequestDto) {
        UUID matchId = matchScoreRequestDto.getMatchUuid();
        OngoingMatch ongoingMatch = ongoingMatchesService.findByUuid(matchId);
        int firstPlayerId = ongoingMatch.getFirstPlayer().getId();
        int secondPlayerId = ongoingMatch.getSecondPlayer().getId();
        int winnerId = matchScoreRequestDto.getRoundWinnerId();
        Match match = new Match(firstPlayerId, secondPlayerId, winnerId);
        saveMatch(match);
        memoryOngoingMatchDao.delete(matchId);
    }

    public void saveMatch(Match match) {
        jpaMatchDao.save(match);
    }
}
