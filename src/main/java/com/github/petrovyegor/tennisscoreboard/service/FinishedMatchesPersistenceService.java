package com.github.petrovyegor.tennisscoreboard.service;

import com.github.petrovyegor.tennisscoreboard.dao.JpaMatchDao;
import com.github.petrovyegor.tennisscoreboard.dao.JpaPlayerDao;
import com.github.petrovyegor.tennisscoreboard.dao.MemoryOngoingMatchDao;
import com.github.petrovyegor.tennisscoreboard.dto.match.MatchesRequestDto;
import com.github.petrovyegor.tennisscoreboard.dto.match.MatchesResponseDto;
import com.github.petrovyegor.tennisscoreboard.dto.match_score.MatchScoreRequestDto;
import com.github.petrovyegor.tennisscoreboard.exception.NotFoundException;
import com.github.petrovyegor.tennisscoreboard.model.OngoingMatch;
import com.github.petrovyegor.tennisscoreboard.model.entity.Match;
import com.github.petrovyegor.tennisscoreboard.model.entity.Player;

import java.util.UUID;

public class FinishedMatchesPersistenceService {
    private final JpaMatchDao jpaMatchDao = new JpaMatchDao();
    private final OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();
    private final MemoryOngoingMatchDao memoryOngoingMatchDao = new MemoryOngoingMatchDao();
    private final JpaPlayerDao jpaPlayerDao = new JpaPlayerDao();

    public void processFinishedMatch(MatchScoreRequestDto matchScoreRequestDto) {
        UUID matchId = matchScoreRequestDto.matchUuid();
        OngoingMatch ongoingMatch = ongoingMatchesService.findByUuid(matchId);

        long winnerId = matchScoreRequestDto.roundWinnerId();
        Player winner = jpaPlayerDao.findById(winnerId).orElseThrow(() -> new NotFoundException("Player with id '%s' does not exist"));
        Match match = new Match(ongoingMatch.getFirstPlayer(), ongoingMatch.getSecondPlayer(), winner);
        jpaMatchDao.save(match);
        memoryOngoingMatchDao.delete(matchId);
    }

    public MatchesResponseDto findMatches(MatchesRequestDto matchesRequestDto) {
        int pageNumber = matchesRequestDto.pageNumber();
        int pageSize = matchesRequestDto.pageSize();
        String filterByName = matchesRequestDto.playerName();
        return jpaMatchDao.findByCriteria(pageNumber, pageSize, filterByName).orElseThrow(() -> new RuntimeException("Failed to retrieve list of finished matches"));
    }
}
