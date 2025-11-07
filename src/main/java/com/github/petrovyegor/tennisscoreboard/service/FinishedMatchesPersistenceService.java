package com.github.petrovyegor.tennisscoreboard.service;

import com.github.petrovyegor.tennisscoreboard.dao.JpaMatchDao;
import com.github.petrovyegor.tennisscoreboard.dao.JpaPlayerDao;
import com.github.petrovyegor.tennisscoreboard.dao.MemoryOngoingMatchDao;
import com.github.petrovyegor.tennisscoreboard.dto.match.MatchRequestDto;
import com.github.petrovyegor.tennisscoreboard.dto.match.PageResultDto;
import com.github.petrovyegor.tennisscoreboard.dto.match_score.MatchScoreRequestDto;
import com.github.petrovyegor.tennisscoreboard.model.OngoingMatch;
import com.github.petrovyegor.tennisscoreboard.model.entity.Match;
import com.github.petrovyegor.tennisscoreboard.model.entity.Player;

import java.util.UUID;

public class FinishedMatchesPersistenceService {
    private final JpaMatchDao jpaMatchDao = new JpaMatchDao();
    private final OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();
    private final MemoryOngoingMatchDao memoryOngoingMatchDao = new MemoryOngoingMatchDao();
    private final JpaPlayerDao jpaPlayerDao = new JpaPlayerDao();
    //Если матч закончился - через `FinishedMatchesPersistenceService` сохраняет
    // законченный матч в базу данных. инкапсулирует чтение и запись законченных
    // матчей в БД

    public void processFinishedMatch(MatchScoreRequestDto matchScoreRequestDto) {
        UUID matchId = matchScoreRequestDto.getMatchUuid();
        OngoingMatch ongoingMatch = ongoingMatchesService.findByUuid(matchId);
        long winnerId = matchScoreRequestDto.getRoundWinnerId();
        Player winner = jpaPlayerDao.findById(winnerId).get();//переписать, а то коряво
        Match match = new Match(ongoingMatch.getFirstPlayer(), ongoingMatch.getSecondPlayer());
        saveMatch(match);
        memoryOngoingMatchDao.delete(matchId);
    }

    public void saveMatch(Match match) {
        jpaMatchDao.save(match);
    }

    public PageResultDto findMatches(MatchRequestDto matchRequestDto) {
        //проверить, если имя null(или ещё page), то выводим всё сплошняком, иначе фильтруем
        PageResultDto pageResultDto =  jpaMatchDao.findByCriteria(matchRequestDto).orElseThrow(() -> new RuntimeException("Exception while find all"));//переписать
        return pageResultDto;
    }
}
