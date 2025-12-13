package com.github.petrovyegor.tennisscoreboard.dto.match_score;

import java.util.UUID;


public record MatchScoreRequestDto(
        UUID matchUuid,
        int roundWinnerId
) {}
