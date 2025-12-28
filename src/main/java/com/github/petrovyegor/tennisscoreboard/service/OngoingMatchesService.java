package com.github.petrovyegor.tennisscoreboard.service;

import com.github.petrovyegor.tennisscoreboard.dao.MemoryOngoingMatchDao;
import com.github.petrovyegor.tennisscoreboard.dto.match.score.MatchScoreResponseDto;
import com.github.petrovyegor.tennisscoreboard.dto.match.score.PlayerScoreDto;
import com.github.petrovyegor.tennisscoreboard.dto.match.newest.NewMatchRequestDto;
import com.github.petrovyegor.tennisscoreboard.dto.match.newest.NewMatchResponseDto;
import com.github.petrovyegor.tennisscoreboard.exception.RestErrorException;
import com.github.petrovyegor.tennisscoreboard.model.match.OngoingMatch;
import com.github.petrovyegor.tennisscoreboard.model.match.PlayerScore;
import com.github.petrovyegor.tennisscoreboard.model.entity.Player;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.UUID;

@Slf4j
public class OngoingMatchesService {
    private final PlayerService playerService = new PlayerService();
    private final MemoryOngoingMatchDao memoryOngoingMatchDao = new MemoryOngoingMatchDao();

    public NewMatchResponseDto createOngoingMatch(NewMatchRequestDto newMatchRequestDto) {
        UUID matchUuid = UUID.randomUUID();
        String firstPlayerName = newMatchRequestDto.firstPlayerName();
        String secondPlayerName = newMatchRequestDto.secondPlayerName();
        Player firstPlayer = playerService.getOrCreatePlayer(firstPlayerName);
        Player secondPlayer = playerService.getOrCreatePlayer(secondPlayerName);
        memoryOngoingMatchDao.save(new OngoingMatch(matchUuid, firstPlayer, secondPlayer));
        return new NewMatchResponseDto(matchUuid);
    }

    public MatchScoreResponseDto getMatchScore(UUID matchUuid) {
        OngoingMatch ongoingMatch = findByUuid(matchUuid);
        return toMatchScoreResponseDto(ongoingMatch);
    }

    public OngoingMatch findByUuid(UUID matchUuid) {
        Optional<OngoingMatch> ongoingMatch = memoryOngoingMatchDao.findById(matchUuid);
        if (ongoingMatch.isPresent()) {
            return ongoingMatch.get();
        }
        log.error("Error while findByUuid executing with parameter '%s'".formatted(matchUuid));
        throw new RestErrorException("Ongoing match with uuid '%s' does not exist!".formatted(matchUuid));
    }

    public boolean isOngoingMatchExist(UUID matchUuid) {
        return memoryOngoingMatchDao.isOngoingMatchExist(matchUuid);
    }

    private MatchScoreResponseDto toMatchScoreResponseDto(OngoingMatch ongoingMatch) {
        PlayerScore firstPlayerScore = ongoingMatch.getFirstPlayerScore();
        PlayerScore secondPlayerScore = ongoingMatch.getSecondPlayerScore();
        PlayerScoreDto firstPlayerScoreDto = toPlayerScoreDto(firstPlayerScore);
        PlayerScoreDto secondPlayerScoreDto = toPlayerScoreDto(secondPlayerScore);
        return new MatchScoreResponseDto(
                ongoingMatch.getUuid(),
                ongoingMatch.getFirstPlayer().getId(),
                ongoingMatch.getSecondPlayer().getId(),
                ongoingMatch.getFirstPlayer().getName(),
                ongoingMatch.getSecondPlayer().getName(),
                firstPlayerScoreDto,
                secondPlayerScoreDto);
    }

    private PlayerScoreDto toPlayerScoreDto(PlayerScore playerScore) {
        return new PlayerScoreDto(
                playerScore.getSets(),
                playerScore.getGames(),
                playerScore.getPoint(),
                playerScore.isAdvantage(),
                playerScore.getTieBreakPoints(),
                playerScore.getFormattedRegularOrTieBreakPoint()
        );
    }
}
