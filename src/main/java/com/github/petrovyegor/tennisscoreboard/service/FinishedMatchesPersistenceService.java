package com.github.petrovyegor.tennisscoreboard.service;

import com.github.petrovyegor.tennisscoreboard.dao.JpaMatchDao;
import com.github.petrovyegor.tennisscoreboard.dao.MemoryOngoingMatchDao;
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

    public void saveMatch(UUID matchUuid){
        OngoingMatch ongoingMatch = ongoingMatchesService.findByUuid(matchUuid);

        jpaMatchDao.save();
    }

    private Match toMatch(OngoingMatch ongoingMatch){
        return new Match()
    }
}
