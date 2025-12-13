package com.github.petrovyegor.tennisscoreboard.dto.match;

import com.github.petrovyegor.tennisscoreboard.model.entity.Match;

import java.util.List;


public record PageResultDto(
        List<Match> content,
        long totalCount,
        int totalPages,
        int pageSize,
        int pageNumber
) {}
