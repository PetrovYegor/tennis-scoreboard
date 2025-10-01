package com.github.petrovyegor.tennisscoreboard.service;

import com.github.petrovyegor.tennisscoreboard.dao.MemoryOngoingMatchDao;
import com.github.petrovyegor.tennisscoreboard.dto.NewMatchRequestDto;
import com.github.petrovyegor.tennisscoreboard.dto.NewMatchResponseDto;
import com.github.petrovyegor.tennisscoreboard.dto.OngoingMatchDto;
import com.github.petrovyegor.tennisscoreboard.dto.PlayerScoreDto;
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
        UUID matchUuid = UUID.randomUUID();
        Player firstPlayer = playerService.getOrCreatePlayer(newMatchRequestDto.getFirstPlayerName());
        Player secondPlayer = playerService.getOrCreatePlayer(newMatchRequestDto.getSecondPlayerName());
        OngoingMatch ongoingMatch = new OngoingMatch(matchUuid, firstPlayer, secondPlayer);
        memoryOngoingMatchDao.save(ongoingMatch);
        return new NewMatchResponseDto(ongoingMatch.getUuid());
    }

    public OngoingMatchDto getMatchState(UUID matchUuid) {
        OngoingMatch ongoingMatch = memoryOngoingMatchDao.findById(matchUuid)
                .orElseThrow(() -> new NotFoundException("Match not found"));
        return convertToDto(ongoingMatch);
    }

    public OngoingMatchDto convertToDto(OngoingMatch ongoingMatch) {
        PlayerScore firstPlayerScore = ongoingMatch.getPlayerScore(ongoingMatch.getFirstPlayer());
        PlayerScore secondPlayerScore = ongoingMatch.getPlayerScore(ongoingMatch.getSecondPlayer());

        return new OngoingMatchDto(
                ongoingMatch.getUuid(),//возможно uuid не нужен в этом дто
                createPlayerScoreDto(ongoingMatch.getFirstPlayer(), firstPlayerScore),
                createPlayerScoreDto(ongoingMatch.getSecondPlayer(), secondPlayerScore)
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
        OngoingMatch ongoingMatch = memoryOngoingMatchDao.findById(matchUuid)
                .orElseThrow(() -> new RestErrorException(ErrorMessage.ONGOING_MATCH_NOT_FOUND_BY_UUID.formatted(matchUuid)));
        return ongoingMatch;
    }

    public v
}
