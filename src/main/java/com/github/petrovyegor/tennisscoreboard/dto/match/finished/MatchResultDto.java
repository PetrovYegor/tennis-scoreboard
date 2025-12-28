package com.github.petrovyegor.tennisscoreboard.dto.match.finished;

import java.util.UUID;

public record MatchResultDto(
        UUID matchUuid,
        boolean isMatchFinished,
        String winnerName
) {}
