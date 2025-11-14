package com.github.petrovyegor.tennisscoreboard.dto.match;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MatchRequestDto {
    private final int pageNumber;
    private final String playerName;
}
