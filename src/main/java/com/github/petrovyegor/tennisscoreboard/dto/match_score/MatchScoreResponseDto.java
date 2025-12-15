package com.github.petrovyegor.tennisscoreboard.dto.match_score;

import java.util.UUID;

public record MatchScoreResponseDto(
        UUID matchUuid,
        Long firstPlayerId,
        Long secondPlayerId,
        String firstPlayerName,
        String secondPlayerName,
        PlayerScoreDto firstPlayerScore,
        PlayerScoreDto secondPlayerScore
) {}

