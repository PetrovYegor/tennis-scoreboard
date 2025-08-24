package com.github.petrovyegor.tennisscoreboard.service;

import com.github.petrovyegor.tennisscoreboard.dao.JpaPlayerDao;
import com.github.petrovyegor.tennisscoreboard.dao.MemoryOngoingMatchDao;
import com.github.petrovyegor.tennisscoreboard.dto.MatchScoreRequestDto;
import com.github.petrovyegor.tennisscoreboard.exception.ErrorMessage;
import com.github.petrovyegor.tennisscoreboard.exception.RestErrorException;
import com.github.petrovyegor.tennisscoreboard.model.OngoingMatch;
import com.github.petrovyegor.tennisscoreboard.model.Player;
import com.github.petrovyegor.tennisscoreboard.model.PlayerScore;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

public class MatchScoreCalculationService {
    //обновляет счёт в матче. Реализует логику подсчёта счёта матча по очкам/геймам/сетам
    //имя, количество сетов, количество геймов, текущее начение Point
    private final JpaPlayerDao jpaPlayerDao = new JpaPlayerDao();
    private final MemoryOngoingMatchDao memoryOngoingMatchDao = new MemoryOngoingMatchDao();

    public MatchScoreRequestDto getGameState(UUID matchUuid) {
        OngoingMatch ongoingMatch = memoryOngoingMatchDao.findById(matchUuid)
                .orElseThrow(() -> new RestErrorException(ErrorMessage.ONGOING_MATCH_NOT_FOUND_BY_UUID));
        Player firstPlayer = jpaPlayerDao.findById(ongoingMatch.getFirstPlayerId()).get();//переписать. Если продолжающийся матч создан, то и игроки должны быть уже созданы
        Player secondPlayer = jpaPlayerDao.findById(ongoingMatch.getSecondPlayerId()).get();
        PlayerScore firstPlayerScore = ongoingMatch.getMatchScore().getPlayersScore().get(firstPlayer.getId());
        PlayerScore secondPlayerScore = ongoingMatch.getMatchScore().getPlayersScore().get(secondPlayer.getId());
        MatchScoreRequestDto matchScoreRequestDto = new MatchScoreRequestDto(
                firstPlayer.getName()
                , secondPlayer.getName()
                , firstPlayerScore.getSets()
                , secondPlayerScore.getSets()
                , firstPlayerScore.getGames()
                , secondPlayerScore.getGames()
                , firstPlayerScore.getCurrentPoint()
                , secondPlayerScore.getCurrentPoint()
        );

        return matchScoreRequestDto;
    }
}
