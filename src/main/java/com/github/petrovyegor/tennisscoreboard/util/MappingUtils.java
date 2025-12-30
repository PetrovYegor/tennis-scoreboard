package com.github.petrovyegor.tennisscoreboard.util;

import com.github.petrovyegor.tennisscoreboard.dto.match.finished.MatchesResponseDto;
import com.github.petrovyegor.tennisscoreboard.dto.match.score.MatchScoreResponseDto;
import com.github.petrovyegor.tennisscoreboard.dto.match.score.PlayerScoreDto;
import com.github.petrovyegor.tennisscoreboard.model.PageResult;
import com.github.petrovyegor.tennisscoreboard.model.match.OngoingMatch;
import com.github.petrovyegor.tennisscoreboard.model.match.PlayerScore;

public class MappingUtils {
    private static PlayerScoreDto toPlayerScoreDto(PlayerScore playerScore) {
        return new PlayerScoreDto(
                playerScore.getSets(),
                playerScore.getGames(),
                playerScore.getPoint(),
                playerScore.isAdvantage(),
                playerScore.getTieBreakPoints(),
                playerScore.getFormattedRegularOrTieBreakPoint()
        );
    }

    public static MatchScoreResponseDto toMatchScoreResponseDto(OngoingMatch ongoingMatch) {
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

    public static MatchesResponseDto toMatchesResponseDto(PageResult pageResult) {
        return new MatchesResponseDto(
                pageResult.content(),
                pageResult.totalCount(),
                pageResult.totalPages(),
                pageResult.pageSize(),
                pageResult.pageNumber()
        );
    }
}
