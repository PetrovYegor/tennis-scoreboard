package com.github.petrovyegor.tennisscoreboard.dto.match;

public record MatchesRequestDto(
        int pageNumber,
        int pageSize,
        String playerName
) {}
