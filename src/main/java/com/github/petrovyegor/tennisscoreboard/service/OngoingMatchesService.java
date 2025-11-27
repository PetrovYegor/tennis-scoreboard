package com.github.petrovyegor.tennisscoreboard.service;

import com.github.petrovyegor.tennisscoreboard.dao.MemoryOngoingMatchDao;
import com.github.petrovyegor.tennisscoreboard.dto.match_score.PlayerScoreDto;
import com.github.petrovyegor.tennisscoreboard.dto.new_match.NewMatchRequestDto;
import com.github.petrovyegor.tennisscoreboard.dto.new_match.NewMatchResponseDto;
import com.github.petrovyegor.tennisscoreboard.dto.ongoing_match.OngoingMatchDto;
import com.github.petrovyegor.tennisscoreboard.exception.ErrorMessage;
import com.github.petrovyegor.tennisscoreboard.exception.NotFoundException;
import com.github.petrovyegor.tennisscoreboard.exception.RestErrorException;
import com.github.petrovyegor.tennisscoreboard.model.OngoingMatch;
import com.github.petrovyegor.tennisscoreboard.model.PlayerScore;
import com.github.petrovyegor.tennisscoreboard.model.entity.Player;

import java.util.UUID;

public class OngoingMatchesService {
    private final PlayerService playerService;
    private final MemoryOngoingMatchDao memoryOngoingMatchDao;

    public OngoingMatchesService() {
        this.playerService = new PlayerService();
        this.memoryOngoingMatchDao = new MemoryOngoingMatchDao();
    }

    public NewMatchResponseDto createOngoingMatch(NewMatchRequestDto newMatchRequestDto) {
        UUID matchUuid = getNewUUID();
        String firstPlayerName = newMatchRequestDto.getFirstPlayerName();
        String secondPlayerName = newMatchRequestDto.getSecondPlayerName();
        Player firstPlayer = playerService.getOrCreatePlayer(firstPlayerName);
        Player secondPlayer = playerService.getOrCreatePlayer(secondPlayerName);
        memoryOngoingMatchDao.save(new OngoingMatch(matchUuid, firstPlayer, secondPlayer));

        return new NewMatchResponseDto(matchUuid);
    }

    public OngoingMatchDto getMatchState(UUID matchUuid) {
        OngoingMatch ongoingMatch = memoryOngoingMatchDao.findById(matchUuid)
                .orElseThrow(() -> new NotFoundException("Match with id '%s' not found".formatted(matchUuid)));//дописать ид в сообщение
        return convertToDto(ongoingMatch);
    }

    public OngoingMatchDto convertToDto(OngoingMatch ongoingMatch) {
        Player firstPlayer = ongoingMatch.getFirstPlayer();
        Player secondPlayer = ongoingMatch.getSecondPlayer();

        PlayerScore firstPlayerScore = ongoingMatch.getFirstPlayerScore();
        PlayerScore secondPlayerScore = ongoingMatch.getSecondPlayerScore();

        return new OngoingMatchDto(
                ongoingMatch.getUuid(),
                createPlayerScoreDto(firstPlayer, firstPlayerScore),
                createPlayerScoreDto(secondPlayer, secondPlayerScore)
        );
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

    public OngoingMatch findByUuid(UUID matchUuid) {
        return memoryOngoingMatchDao.findById(matchUuid)
                .orElseThrow(() -> new RestErrorException(ErrorMessage.ONGOING_MATCH_NOT_FOUND_BY_UUID.formatted(matchUuid)));
    }

    public boolean isOngoingMatchExist(UUID matchUuid){
        return memoryOngoingMatchDao.isOngoingMatchExist(matchUuid);
    }

    private UUID getNewUUID() {
        return UUID.randomUUID();
    }
}
