package com.github.petrovyegor.tennisscoreboard.dto.finished_match;

import java.util.UUID;

public record MatchResultDto(
        UUID matchUuid,
        boolean isMatchFinished,
        String winnerName
) {}
