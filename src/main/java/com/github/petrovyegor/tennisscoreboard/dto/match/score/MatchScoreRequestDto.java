package com.github.petrovyegor.tennisscoreboard.dto.match.score;

import java.util.UUID;


public record MatchScoreRequestDto(
        UUID matchUuid,
        int roundWinnerId
) {}
