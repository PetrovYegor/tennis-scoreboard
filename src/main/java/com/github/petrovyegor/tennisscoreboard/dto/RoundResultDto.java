package com.github.petrovyegor.tennisscoreboard.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RoundResultDto {
    private final UUID matchUuid;
    private final boolean isMatchFinished;
    private final int firstPlayerId;
    private final int secondPlayerId;
    private final int winnerId;
}
