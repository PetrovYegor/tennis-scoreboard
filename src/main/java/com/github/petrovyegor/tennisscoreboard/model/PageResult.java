package com.github.petrovyegor.tennisscoreboard.model;

import com.github.petrovyegor.tennisscoreboard.model.entity.Match;

import java.util.List;

public record PageResult (
        List<Match> content,
        long totalCount,
        int totalPages,
        int pageSize,
        int pageNumber
) {}
