package com.github.petrovyegor.tennisscoreboard.dto.match;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MatchesRequestDto {
    private final int pageNumber;
    private final int pageSize;
    private final String playerName;
}
