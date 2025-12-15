package com.github.petrovyegor.tennisscoreboard.service;

import com.github.petrovyegor.tennisscoreboard.dao.MemoryOngoingMatchDao;
import com.github.petrovyegor.tennisscoreboard.dto.match_score.RoundResultDto;
import com.github.petrovyegor.tennisscoreboard.dto.match_score.PlayerScoreDto;
import com.github.petrovyegor.tennisscoreboard.dto.new_match.NewMatchRequestDto;
import com.github.petrovyegor.tennisscoreboard.dto.new_match.NewMatchResponseDto;
import com.github.petrovyegor.tennisscoreboard.exception.RestErrorException;
import com.github.petrovyegor.tennisscoreboard.model.OngoingMatch;
import com.github.petrovyegor.tennisscoreboard.model.PlayerScore;
import com.github.petrovyegor.tennisscoreboard.model.entity.Player;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.UUID;

@Slf4j
public class OngoingMatchesService {
    private final PlayerService playerService = new PlayerService();
    private final MemoryOngoingMatchDao memoryOngoingMatchDao = new MemoryOngoingMatchDao();

    public NewMatchResponseDto createOngoingMatch(NewMatchRequestDto newMatchRequestDto) {
        UUID matchUuid = getNewUUID();
        String firstPlayerName = newMatchRequestDto.firstPlayerName();
        String secondPlayerName = newMatchRequestDto.secondPlayerName();
        Player firstPlayer = playerService.getOrCreatePlayer(firstPlayerName);
        Player secondPlayer = playerService.getOrCreatePlayer(secondPlayerName);
        memoryOngoingMatchDao.save(new OngoingMatch(matchUuid, firstPlayer, secondPlayer));

        return new NewMatchResponseDto(matchUuid);
    }

    public RoundResultDto getMatchScore(UUID matchUuid) {
        return convertToDto(findByUuid(matchUuid));
    }

    public RoundResultDto convertToDto(OngoingMatch ongoingMatch) {
        Player firstPlayer = ongoingMatch.getFirstPlayer();
        Player secondPlayer = ongoingMatch.getSecondPlayer();

        PlayerScore firstPlayerScore = ongoingMatch.getFirstPlayerScore();
        PlayerScore secondPlayerScore = ongoingMatch.getSecondPlayerScore();

        return new RoundResultDto(
                ongoingMatch.getUuid(),
                createPlayerScoreDto(firstPlayer, firstPlayerScore),
                createPlayerScoreDto(secondPlayer, secondPlayerScore)
        );
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

    private UUID getNewUUID() {
        return UUID.randomUUID();
    }

    private PlayerScoreDto createPlayerScoreDto(Player player, PlayerScore playerScore) {
        return new PlayerScoreDto(
                player.getId(),
                player.getName(),
                playerScore.getSets(),
                playerScore.getGames(),
                playerScore.getPoint(),
                playerScore.isAdvantage(),
                playerScore.getTieBreakPoints(),
                playerScore.getFormattedRegularOrTieBreakPoint()
        );
    }
}
