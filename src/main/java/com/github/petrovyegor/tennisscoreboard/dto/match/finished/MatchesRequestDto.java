package com.github.petrovyegor.tennisscoreboard.dto.match.finished;

public record MatchesRequestDto(
        int pageNumber,
        int pageSize,
        String playerName
) {}
